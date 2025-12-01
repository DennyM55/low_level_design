# StackOverflow — LLD Design

## Requirements Clarification

Questions to ask:

- Can anyone (including guests) search and view questions?
- What actions require registration? (Post, vote, comment)
- Can questions have bounties?
- Can content contain photos/attachments?
- How do badges work — who assigns them?
- What can a Moderator do that a regular Member cannot?
- What can an Admin do that a Moderator cannot?

## Scope for This Design

- Guests can search and view. Members can post, vote, comment.
- Questions can have answers; both can have comments.
- Each question can have one active bounty.
- Content (questions, answers) can contain photos.
- Members earn badges for activity milestones.
- Moderators can close questions.
- Admins can block members.

---

## Use Cases

| Actor | Use Case |
|---|---|
| Guest | Search questions, view questions and answers |
| Member | All Guest use cases + post question, post answer, post comment, vote up/down, add bounty |
| Moderator | All Member use cases + close question |
| Admin | All Member use cases + block member |

---

## Entities

### Question

```java
class Question {
    QuestionStatus status;  // OPEN, CLOSED
    String title;
    String description;
    int votes;
    List<Answer> answers;       // composition
    List<Comment> comments;     // composition
    Bounty bounty;              // optional
    List<Photo> photos;         // optional

    void close() { this.status = QuestionStatus.CLOSED; }
}
```

---

### Answer

```java
class Answer {
    String content;
    int votes;
    boolean accepted;
    List<Comment> comments;   // composition
}
```

---

### Comment

```java
class Comment {
    String content;
    int votes;
}
```

---

### Bounty

A prize offered on a question to attract high-quality answers.

```java
class Bounty {
    int prize;
    DateTime expiry;

    void claim() { /* award prize to accepted answer author */ }
}
```

---

### Photo

```java
class Photo {
    String altText;
    String url;
}
```

---

### Badge

Badges are shared across members. Deleting a member does not delete the badge; deleting a badge does not delete the member. This is an **association** (many-to-many), NOT composition.

```java
class Badge {
    String text;  // e.g., "Good Question", "Popular Answer"
}
```

---

### Member

```java
class Member {
    int age;
    String sex;
    Account account;    // composition — account belongs to member

    void createQuestion() {}
    void postAnswer() {}
    void postComment() {}
    void vote() {}
}
```

---

### Moderator

Extends Member. Can close questions.

```java
class Moderator extends Member {
    void closeQuestion(Question q) { q.close(); }
}
```

---

### Admin

Extends Member. Can block users.

```java
class Admin extends Member {
    void blockMember(Member m) { m.getAccount().setBlocked(true); }
}
```

---

### Account

```java
class Account {
    String password;
    DateTime creationDate;
    boolean isBlocked;

    void deleteAccount() {}
}
```

Relationship: `Member 1 <>-- 1 Account` (composition — account belongs to member, deleted together)

---

### Guest

Does NOT extend Member. A guest has no account yet.

```java
class Guest {
    void register() { /* creates a new Member + Account */ }
}
```

Both `Guest` and `Member` can use the `Search` interface.

---

### Search (Interface)

```java
interface Search {
    List<Question> searchByKeyword(String keyword);
    List<Question> searchByTag(String tag);
    List<Question> searchByUser(Member member);
}
```

---

## Full Class Diagram (Text)

```
QuestionStatus (enum): OPEN, CLOSED

Question
  - status: QuestionStatus
  - title: String
  - description: String
  - votes: int
  - answers: List<Answer>     (composition)
  - comments: List<Comment>   (composition)
  - bounty: Bounty            (optional association)
  - photos: List<Photo>
  + close(): void

Answer
  - content: String
  - votes: int
  - accepted: boolean
  - comments: List<Comment>   (composition)

Comment
  - content: String
  - votes: int

Bounty
  - prize: int
  - expiry: DateTime
  + claim(): void

Photo
  - altText: String
  - url: String

Badge
  - text: String

Account
  - password: String
  - creationDate: DateTime
  - isBlocked: boolean
  + deleteAccount(): void

Member
  - age: int
  - account: Account         (composition)
  + createQuestion(): void
  + postAnswer(): void
  + vote(): void

Moderator extends Member
  + closeQuestion(q): void

Admin extends Member
  + blockMember(m): void

Guest
  + register(): void

<<interface>> Search
  + searchByKeyword(keyword): List<Question>
  + searchByTag(tag): List<Question>
```

---

## Relationships Summary

```
Member 1 <>------ 1 Account                (composition — account tied to member)
Question <>------ * Answer                 (composition — answers belong to question)
Question <>------ * Comment                (composition — comments belong to question)
Answer <>------ * Comment                  (composition — comments belong to answer)
Member ------ * Badge                      (association — many-to-many, neither owns the other)
Member ---> * Question                     (association — member created question, not owned)
Moderator extends Member
Admin extends Member
Guest --uses--> Search                     (dependency)
Member --uses--> Search                    (dependency)
```

---

## Key Design Decisions

| Decision | Reasoning |
|---|---|
| Question `<>--` Answer/Comment | Deleting a question deletes its answers and comments — composition |
| Member `--` Badge | Many-to-many; deleting either does not affect the other — association |
| Guest does NOT extend Member | Guest has no account and limited capabilities; inheritance would break LSP |
| Search as interface | Search can be implemented for questions, users, tags — extensible |
| Moderator/Admin extend Member | They ARE members with additional permissions — inheritance is correct here |

---

## Design Patterns Applicable

| Pattern | Where | Why |
|---|---|---|
| Observer | New answer posted | Notify question author and bounty subscribers |
| Strategy | Search implementations | Different search algorithms (keyword, tag, user) |
| Decorator | Adding photo attachments to content | Wrap content with optional attachments |
| Singleton | Search/Catalogue service | One shared search index |
