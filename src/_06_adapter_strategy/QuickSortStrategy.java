package _06_adapter_strategy;

import java.util.Arrays;

public class QuickSortStrategy implements SortStrategy {

    @Override
    public void sort(int[] data) {
        quickSort(data, 0, data.length - 1);
        System.out.println("[QuickSort]  Sorted: " + Arrays.toString(data));
    }

    private void quickSort(int[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            quickSort(data, low, pi - 1);
            quickSort(data, pi + 1, high);
        }
    }

    private int partition(int[] data, int low, int high) {
        int pivot = data[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (data[j] <= pivot) {
                i++;
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
        int temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;
        return i + 1;
    }
}
