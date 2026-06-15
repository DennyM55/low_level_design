# Google Calendar — LLD Design

## Requirements Clarification

Questions to ask:

- Can a user create, update, and delete events?
- Can an event have multiple attendees?
- Can attendees RSVP (accept, decline, maybe)?
- Do we need recurring events?
- Can two events overlap on the same calendar?
- Do we need to send notifications/reminders?
- Do we need time zone support?
- Can a user view other users' calendars?

## Scope for This Design

- A user has a calendar
- A user can create, update, and delete events
- Events can have multiple attendees
- Attendees can RSVP
- Event reminder support
- No recurring events in this version
- No time zone handling in this version

---

## Entities

### User

Fields: `userId`, `name`, `email`

---

### Calendar

Fields: `calendarId`, `owner`, `events`

One user owns one primary calendar.

Methods:
- `addEvent(event)` — adds event after checking for conflicts
- `removeEvent(eventId)` — removes event
- `getEventsForDay(date)` — returns events on that day
- `hasConflict(event)` — checks if a new event overlaps any existing event

---

### Event

Fields: `eventId`, `title`, `description`, `startTime`, `endTime`, `organizer`, `attendees`, `status`

Methods:
- `addAttendee(invite)` — adds an EventInvite for an attendee
- `updateStatus(inviteStatus)` — updates RSVP status

---

### EventStatus (Enum)

Values: `SCHEDULED`, `CANCELLED`

---

### EventInvite

Tracks a single attendee's RSVP.

Fields: `user`, `inviteStatus`

---

### InviteStatus (Enum)

Values: `PENDING`, `ACCEPTED`, `DECLINED`, `MAYBE`

---

### Reminder

Fields: `reminderId`, `event`, `reminderTime`, `reminderType`

---

### ReminderType (Enum)

Values: `EMAIL`, `NOTIFICATION`

---

### CalendarService

Manages calendar operations.

Methods:
- `createEvent(organizer, title, start, end, attendees)` → Event
- `cancelEvent(organizer, eventId)` — only organizer can cancel
- `rsvp(userId, eventId, status)` — update invite status
- `getEventsForUser(userId, date)` — list events

---

## Class Diagram (Text)

```
EventStatus (enum): SCHEDULED, CANCELLED
InviteStatus (enum): PENDING, ACCEPTED, DECLINED, MAYBE
ReminderType (enum): EMAIL, NOTIFICATION

User
  - userId: String
  - name: String
  - email: String

EventInvite
  - user: User
  - inviteStatus: InviteStatus

Event
  - eventId: String
  - title: String
  - description: String
  - startTime: LocalDateTime
  - endTime: LocalDateTime
  - organizer: User
  - attendees: List<EventInvite>
  - status: EventStatus
  + addAttendee(invite): void
  + cancel(): void

Reminder
  - reminderId: String
  - event: Event
  - reminderTime: LocalDateTime
  - reminderType: ReminderType

Calendar
  - calendarId: String
  - owner: User
  - events: Map<String, Event>
  + addEvent(event): void
  + removeEvent(eventId): void
  + hasConflict(event): boolean
  + getEventsForDay(date): List<Event>

CalendarService
  - userCalendars: Map<String, Calendar>
  + createEvent(...): Event
  + cancelEvent(userId, eventId): void
  + rsvp(userId, eventId, status): void
```

---

## Relationships

```
User has-a (composition) Calendar              → 1 to 1
Calendar has-a (composition) List<Event>       → 1 to many
Event has-a (composition) List<EventInvite>    → 1 to many
EventInvite is associated with User            → 1 to 1
Event is associated with User (organizer)      → many to 1
Reminder is associated with Event              → many to 1
```

---

## Conflict Detection

Two events conflict if they overlap in time.

```
Event A: 10:00 to 11:00
Event B: 10:30 to 11:30  ← conflicts with A
Event C: 11:00 to 12:00  ← does not conflict (starts exactly when A ends)
```

Overlap condition:

```
newEvent.startTime < existing.endTime
AND
newEvent.endTime > existing.startTime
```

---

## Design Patterns Applied

| Pattern | Where | Why |
|---|---|---|
| Observer | Reminder / notification | Notify attendees when event is created or cancelled |
| Builder | Event | Many optional fields (description, attendees, reminder) |
| Singleton | CalendarService | One service manages all calendars |

---

## Event Creation Flow (Sequence)

```
User
│
├── CalendarService.createEvent(organizer, title, start, end, attendees)
│
├── Check organizer's calendar for conflicts
│   └── hasConflict(newEvent) → throws exception if conflict found
│
├── Create Event object
│
├── Add EventInvite (PENDING) for each attendee
│
├── Add event to organizer's calendar
│
├── For each attendee, add event to their calendar
│
└── Send invitations (observer/notification)
```

---

## RSVP Flow

```
Attendee
│
├── CalendarService.rsvp(userId, eventId, ACCEPTED)
│
├── Find the EventInvite for this user in the event
│
└── Update inviteStatus to ACCEPTED
```

---

## Key Design Decisions

| Decision | Reasoning |
|---|---|
| `Event` does not check conflicts | Single responsibility: Calendar checks conflicts, Event only stores data |
| `EventInvite` is a separate class | The invite status belongs to the attendee-event relationship, not the user or event alone |
| Calendar per user | Each user has their own view of events, including events they were invited to |
| `CalendarService` is the entry point | Centralises all calendar operations and inter-user coordination |
