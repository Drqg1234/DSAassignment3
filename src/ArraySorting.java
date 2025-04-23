// Mamaguevo, digo glug glug glug glug
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class ArraySorting {
    // Insertion sort
    public static void insertionSort(int[] arr){

        for (int i = 1; i < arr.length; i++){
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Heap sort and methods
    public static void heapify(int[] arr, int n, int i){
        int largest = i;

        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]){
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]){
            largest = right;
        }

        if (largest != i){
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            heapify(arr, n, largest);
        }
    }
    public static void heapSort(int[] arr){
        int length = arr.length;

        for (int i = (length / 2) - 1; i >= 0; i--){
            heapify(arr, length, i);
        }

        for (int i = length - 1; i > 0; i--){
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    // Merge sort and methods
    public static void mergeSort(int[] arr){
        if (arr == null || arr.length <= 1){
            return;
        }
        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);
    }
    public static void mergeSort(int[] arr, int[] temp, int left, int right){
        if (left < right){
            int mid = left + (right - left) / 2;
            mergeSort(arr, temp, left, mid);
            mergeSort(arr, temp, mid + 1, right);
            merge(arr, temp, left, mid, right);
        }
    }
    public static void merge(int[] arr, int[] temp, int left, int mid, int right){
        System.arraycopy(arr, left, temp, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right){
            if (temp[i] <= temp[j]){
                arr[k] = temp[i];
                i++;
            }
            else{
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= mid){
            arr[k] = temp[i];
            k++; i++;
        }
    }

    // Quick sort and methods
    public static void quickSort(int[] arr){
        if (arr == null || arr.length <= 1){
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }
    public static void quickSort(int[] arr, int low, int high){
        if (low < high){
            int pivot = partition(arr, low, high);

            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }
    public static void quickSortCutoff(int[] arr, int cutoff){
        if (arr == null || arr.length <= 1){
            return;
        }
        quickSortCutoff(arr, 0, arr.length - 1, cutoff);
    }
    public static void quickSortCutoff(int[] arr, int low, int high, int cutoff){
        if (high - low <= cutoff){
            insertionCutoff(arr, low, high);
        }
        else{
            int pivot = partition(arr, low, high);
            quickSortCutoff(arr, low, pivot - 1, cutoff);
            quickSortCutoff(arr, pivot + 1 , high, cutoff);
        }
    }
    public static void insertionCutoff(int[] arr, int low, int high){
        for (int i = low + 1; i <= high; i++){
            int key = arr[i];
            int j = i - 1;

            while (j >= low && arr[j] > key){
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    public static int partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++){
            if (arr[j] < pivot){
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }
    public static void swap(int[] arr, int i , int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Generate different types of arrays
    public static int[] generateArray(int size){
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++){
            arr[i] = rand.nextInt(size * 100);
        }
        return arr;
    }
    public static int[] generateNearlySortedArray(int size){
        Random rand = new Random();
        int[] arr = generateArray(size);
        Arrays.sort(arr);
        for (int i = 0; i < size / 100; i++){ // Switches random numbers in the array to test how well it does with a nearly sorted array
            swap(arr, rand.nextInt(size), rand.nextInt(size));
        }
        return arr;
    }
    public static int[] generateReverseSortedArray(int size){
        int[] arr = generateArray(size);
        Arrays.sort(arr);
        for (int i = 0; i < arr.length / 2; i++){
            swap(arr, i , arr.length - 1 - i);
        }
        return arr;
    }

    public static void testSorts(int size){
        System.out.println("<---------------Array Size: " + size + "--------------->");
        
        int[] random = generateArray(size);
        int[] nearly = generateNearlySortedArray(size);
        int[] reverse = generateReverseSortedArray(size);

        testArray("Random", random);
        testArray("Nearly", nearly);
        testArray("Reverse", reverse);
    }

    public static void testArray(String name, int[] originalArr){
        System.out.println("\nCurrently Testing Array: " + name);
        System.out.printf("%-25s %8s %5s\n", "Sort", "Time(ms)", "Memory(KB)");
        System.out.println("----------------------------------------------");

        testSortingAlgorithm("Insertion: ", originalArr, ArraySorting::insertionSort);
        testSortingAlgorithm("Heap: ", originalArr, ArraySorting::heapSort);
        testSortingAlgorithm("Merge: ", originalArr, ArraySorting::mergeSort);
        testSortingAlgorithm("Quick: ", originalArr, ArraySorting::quickSort);

        testSortingAlgorithm("Quick (cutoff at  10): ", originalArr, arr -> quickSortCutoff(arr, 10));
        testSortingAlgorithm("Quick (cutoff at  50): ", originalArr, arr -> quickSortCutoff(arr, 50));
        testSortingAlgorithm("Quick (cutoff at 200): ", originalArr, arr -> quickSortCutoff(arr, 200));
    }

    public static void testSortingAlgorithm(String name, int[] originalArr, Consumer<int[]> sorter){
        int[] arr = Arrays.copyOf(originalArr, originalArr.length);
        
        System.gc();
        
        Runtime rt = Runtime.getRuntime();
        long usedMemoryBefore = rt.totalMemory() - rt.freeMemory();
        
        long timeStart = System.nanoTime();
        
        sorter.accept(arr);
        
        long timeEnd = System.nanoTime();
        
        long usedMemoryAfter = rt.totalMemory() - rt.freeMemory();
        
        double time = (timeEnd - timeStart) / 1_000_000.0;
        double memoryUsed = (usedMemoryAfter - usedMemoryBefore) / 1024.0; 
        
        memoryUsed = Math.max(0, memoryUsed);
        
        System.out.printf("%-25s %8.2f %10.2f\n", name, time, memoryUsed);
    }

    public static void main(String[] args) {
        int[] arraySizes = {50, 500, 1_000, 2_000, 5_000, 10_000};

        for (int size : arraySizes){
            testSorts(size);
            System.out.println();
        }
    }
}
