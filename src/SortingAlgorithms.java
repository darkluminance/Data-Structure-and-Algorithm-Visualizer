import java.awt.*;

public class SortingAlgorithms {

    public void bubbleSort(SortArray arr){
        int len=arr.arrSize;
        int temp=0;
        arr.isSorting = true;
        arr.comparisons = 0;
        for(int i=0;i<len-1;i++){
            for(int j=0;j<len-i-1;j++){
                if (!arr.isSorting){
                    return;
                }
                arr.barColor[j] = new Color(77, 171, 213);
                arr.sleep(arr.animSpeed);
                arr.Update();

                if(arr.getValue(j)>arr.getValue(j+1)){
                    arr.swap(j, j+1);
                }
                arr.Update();
                arr.increaseComparison();
                arr.barColor[j] = Color.white;
                arr.barColor[j+1] = Color.white;
            }
            arr.barColor[len-i-1] = new Color(226, 86, 186);
            arr.Update();
        }
        arr.sortAnim();
        arr.isSorting = false;
        System.out.println("Sorted");
    }

    public void insertionSort(SortArray arr) {
        int n = arr.arrSize;
        arr.isSorting = true;
        arr.comparisons = 0;
        for (int i = 1; i < n; i++) {
            int temp = arr.getValue(i);
            int j = i;
            arr.barColor[i] = new Color(77, 171, 213);
            arr.sleep(arr.animSpeed);
            arr.Update();

            while ((j > 0) && (arr.getValue(j-1) > temp)) {
                if (!arr.isSorting){
                    return;
                }
                arr.barColor[j] = Color.red;
                arr.barColor[j-1] = Color.red;
                arr.setValueatIndex(j, j-1);
                arr.sleep(arr.animSpeed);
                arr.Update();
                arr.increaseComparison();
                arr.barColor[j] = Color.white;
                arr.barColor[j-1] = Color.white;
                j--;
            }
            arr.setValue(j, temp);
            arr.barColor[i] = Color.white;
            arr.Update();
        }
        arr.sortAnim();
        arr.isSorting = false;
        System.out.println("Sorted");
    }

    public void selectionSort(SortArray arr){
        arr.isSorting = true;
        arr.comparisons = 0;
        for(int i=0;i<arr.arrSize-1;i++){
            int min=i;
            arr.barColor[i] = new Color(128, 86, 226);
            arr.Update();

            for(int j=i+1;j<arr.arrSize;j++){
                if (!arr.isSorting){
                    return;
                }

                arr.barColor[j] = new Color(86, 193, 226);
                if (min != i)
                    arr.barColor[min] = new Color(226, 160, 86);


                arr.Update();

                if(arr.getValue(j)<arr.getValue(min)){
                    if (min != i)
                        arr.barColor[min] = Color.white;

                    min=j;
                    arr.barColor[min] = new Color(226, 160, 86);
                }
                arr.sleep(arr.animSpeed);
                arr.Update();
                arr.increaseComparison();

                arr.barColor[j] = Color.white;
                arr.Update();
            }
            arr.barColor[i] = Color.white;

            arr.Update();
            arr.swap(i, min);
            arr.barColor[i] = new Color(226, 86, 186);
            arr.Update();

        }
        arr.sortAnim();
        arr.isSorting = false;
        System.out.println("Sorted");
    }

    public void merge(SortArray arr,int start,int mid,int end){
        int l=mid-start+1;
        int r=end-mid;
        int leftarr[]=new int[l];
        int rightarr[]=new int[r];

        for(int i=0;i<l;i++){
            leftarr[i] = arr.getValue(start+i);
        }
        for(int j=0;j<r;j++){
            rightarr[j] = arr.getValue(mid+j+1);
        }

        int i=0;
        int j=0;
        int k=start;

        while(i<l&&j<r){
            if (k != start && k!= end)
                arr.barColor[k] = new Color(226, 160, 86);

            if (!arr.isSorting){
                return;
            }

            if(leftarr[i]<=rightarr[j]){
                arr.setValue(k, leftarr[i]);
                i++;
            }
            else{
                arr.setValue(k, rightarr[j]);
                j++;
            }
            k++;
            arr.sleep(arr.animSpeed);
            arr.Update();
            arr.increaseComparison();
        }
        while (i<l){
            if (k != start && k!= end)
                arr.barColor[k] = new Color(226, 160, 86);

            if (!arr.isSorting){
                return;
            }

            arr.setValue(k, leftarr[i]);
            i++;
            k++;
            arr.sleep(arr.animSpeed);
            arr.Update();
        }
        while (j<r){
            if (k != start && k!= end)
                arr.barColor[k] = new Color(226, 160, 86);

            if (!arr.isSorting){
                return;
            }

            arr.setValue(k, rightarr[j]);
            j++;
            k++;
            arr.sleep(arr.animSpeed);
            arr.Update();
        }
    }
    public void mergesort(SortArray arr,int start,int end){
        if(start<end){
            int mid=(start+end)/2;

            arr.barColor[start] = new Color(103, 175, 208);
            arr.barColor[end] = new Color(208, 111, 157);

            arr.Update();   arr.sleep(5);

            mergesort(arr,start,mid);
            mergesort(arr,mid+1,end);
            merge(arr,start,mid,end);

            arr.Update();
        }
    }

    public void sortdeMerge(SortArray arr){
        arr.isSorting = true;
        arr.comparisons = 0;

        mergesort(arr, 0, arr.arrSize-1);

        arr.sortAnim();
        arr.isSorting = false;
        System.out.println("Sorted");
    }
}
