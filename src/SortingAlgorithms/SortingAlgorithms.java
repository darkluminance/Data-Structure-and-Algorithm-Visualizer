package SortingAlgorithms;

import java.awt.*;

public class SortingAlgorithms {

    //Bubble sort algorithm
    public void bubbleSort(SortArray arr){
        int len=arr.arrSize;
        arr.isSorting = true;
        arr.comparisons = 0;
        for(int i=0;i<len-1;i++){
            for(int j=0;j<len-i-1;j++){
                //Check if stop button has been pressed in the last frame
                if (!arr.isSorting){
                    return;
                }
                arr.barColor[j] = arr.blueColor;  //The current element

                //For some reason first element doesn't animate so had to update manually  :)
                if (i == 0 && j == 0){
                    arr.Update();
                }

                arr.sleep(arr.animSpeed);
                arr.Update();
                arr.barColor[j] = Color.white;

                //Compares the current element with the next element and swaps them if condition is true
                //Swapped elements will show red in the animation
                if(arr.getValue(j)>arr.getValue(j+1)){
                    arr.swap(j, j+1);
                }

                arr.Update();
                arr.increaseComparison();
            }
            arr.barColor[len-i-1] = arr.pinkColor;
        }
        //Performs the sorted animation
        arr.sortAnim();
    }

    //Insertion sort algorithm
    public void insertionSort(SortArray arr) {
        int n = arr.arrSize;
        arr.isSorting = true;
        arr.comparisons = 0;
        for (int i = 1; i < n; i++) {
            /*storing current element whose left side is checked for its
             correct position .*/
            int temp = arr.getValue(i);
            int j = i;

            arr.barColor[i] = arr.blueColor;
            arr.Update();
            arr.sleep(arr.animSpeed);

            //Inserting arr[i] to the correct position
            /* check whether the adjacent element in left side is greater or
            less than the current element. */
            while ((j > 0) && (arr.getValue(j-1) > temp)) {
                if (!arr.isSorting){
                    return;
                }

                //Showing which element is being compared to arr[i] i.e arr[j] currently
                arr.barColor[j] = Color.red;
                arr.barColor[j-1] = Color.red;
                arr.Update();
                arr.sleep(arr.animSpeed);

                //arr[j] will now go back one position back so next iteration arr[j] will be arr[j-1]
                arr.setValueatIndex(j, j-1);
                arr.increaseComparison();
                arr.barColor[j] = Color.white;
                arr.barColor[j-1] = Color.white;
                j--;
            }
            //Setting arr[j]'s original value
            arr.setValue(j, temp);
            arr.barColor[i] = Color.white;
        }
        arr.sortAnim();
    }

    //Selection sort algorithm
    public void selectionSort(SortArray arr){
        arr.isSorting = true;
        arr.comparisons = 0;
        for(int i=0;i<arr.arrSize-1;i++){
            int min=i;      //First, let the minimum element be the arr[i], then start comparing

            arr.barColor[i] = arr.purpleColor;      //The current position where minimum element will be swapped with

            for(int j=i+1;j<arr.arrSize;j++){
                if (!arr.isSorting){
                    return;
                }

                arr.barColor[j] = arr.blueColor;            //The current element in loop
                if (min != i)
                    arr.barColor[min] = arr.orangeColor;    //Current minimum element. Will ignore if it's the arr[i]


                arr.Update();

                if(arr.getValue(j)<arr.getValue(min)){      //Compares current element with current minimum
                    if (min != i)
                        /*a[min] is going to be changed, so this element should be turned back
                        to white to prevent multiple orange colors
                         */
                        arr.barColor[min] = Color.white;

                    min=j;                                  //New position of a[min]
                    arr.barColor[min] = arr.orangeColor;    //Setting new a[min] the color
                }

                arr.sleep(arr.animSpeed);
                arr.increaseComparison();

                arr.barColor[j] = Color.white;
                arr.Update();
            }
            arr.barColor[i] = Color.white;

            //Swap the minimum element with the arr[i]
            arr.swap(i, min);
            arr.barColor[i] = arr.pinkColor;    //This means that this place is perfectly sorted

        }
        arr.sortAnim();
    }

    public void merge(SortArray arr,int start,int mid,int end){
        int l=mid-start+1;      //Size of left half array
        int r=end-mid;          //Size of right half array
        int[] leftarr =new int[l];
        int[] rightarr =new int[r];

        for(int i=0;i<l;i++){
            leftarr[i] = arr.getValue(start+i);
        }
        for(int j=0;j<r;j++){
            rightarr[j] = arr.getValue(mid+j+1);
        }

        int i=0;
        int j=0;
        int k=start;

        //Merging mechanism
        while(i<l&&j<r){
            if (k != start && k!= end)
                arr.barColor[k] = arr.orangeColor;      //Setting color of merged array

            if (!arr.isSorting){
                return;
            }

            /*
            If current element of the left array is not greater than
            current element of the right array,
            then set the current element of arr[] equal to the
            value of leftarr[i], otherwise current arr[] = rightarr[j]
             */
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
                arr.barColor[k] = arr.orangeColor;

            if (!arr.isSorting){
                return;
            }

            arr.setValue(k, leftarr[i]);
            i++;
            k++;
            //arr.sleep(arr.animSpeed);
            arr.Update();
            arr.increaseComparison();
        }
        while (j<r){
            if (k != start && k!= end)
                arr.barColor[k] = arr.orangeColor;

            if (!arr.isSorting){
                return;
            }

            arr.setValue(k, rightarr[j]);
            j++;
            k++;
            //arr.sleep(arr.animSpeed);
            arr.Update();
            arr.increaseComparison();
        }
    }

    public void mergee(SortArray A, int start, int mid, int end){
        //stores the starting position of both parts in temporary variables.
        int p = start, q = mid + 1;
        int r = start;
        int s = end;

        int[] Arr = new int[end - start + 1];
        int k = 0;

        if (!A.isSorting){
            return;
        }

        Color startColorA =  A.barColor[start];
        Color startColorB =  A.barColor[end];

        A.barColor[start] = new Color(0, 184, 148);
        A.barColor[end] = new Color(0, 184, 148);

        A.Update();
        A.sleep(A.animSpeed);

        if (!A.isSorting){
            return;
        }

        A.barColor[start] = startColorA;
        A.barColor[end] = startColorB;

        A.Update();
        A.sleep(A.animSpeed);

        for (int i = start; i <= end; i++)
        {
            if (!A.isSorting){
                return;
            }

            if (p > mid) //checks if first part comes to an end or not .
                Arr[k++] = A.getValue(q++);

            else if (q > end) //checks if second part comes to an end or not
                Arr[k++] = A.getValue(p++);

            else if (A.getValue(p) < A.getValue(q)) //checks which part has smaller element.
                Arr[k++] = A.getValue(p++);

            else
                Arr[k++] = A.getValue(q++);
        }
        for (int i = 0; i < k; i++)
        {
            if (!A.isSorting){
                return;
            }
        /* Now the real array has elements in sorted manner including both
            parts.*/

            A.barColor[start] = A.purpleColor;

            A.Update();
            A.sleep(A.animSpeed);
            A.increaseComparison();

            A.setValue(start++, Arr[i]);


            if  (start-1 != r && start-1!= s)
                A.barColor[start-1] = A.orangeColor;

            A.Update();

            if  (start-1 != r && start-1!= s)
                A.sleep(A.animSpeed);

            if  (start-1 == r)
            {
                A.barColor[r] = A.blueColor;
                A.sleep(A.animSpeed);
            }
            else if (start-1 == s)
            {
                A.barColor[s] = A.pinkColor;
                A.sleep(A.animSpeed);
            }

        }
    }
    public void mergesort(SortArray arr,int start,int end){
        arr.barColor[start] = Color.red;
        arr.barColor[end] = Color.red;

        arr.Update();
        arr.sleep(arr.animSpeed);

        arr.barColor[start] = arr.blueColor;
        arr.barColor[end] = arr.pinkColor;

        if (start == end){
            arr.barColor[start] = new Color(9, 132, 227);
        }

        arr.Update();
        arr.sleep(arr.animSpeed);

        if(start<end){
            if (!arr.isSorting){
                return;
            }

            int mid=(start+end)/2;      //defines the current array in 2 parts

            mergesort(arr,start,mid);       // sort the 1st part of array
            mergesort(arr,mid+1,end);  // sort the 2nd part of array

            // merge the both parts by comparing elements of both the parts
            mergee(arr,start,mid,end);

            arr.Update();
        }
    }

    public void sortdeMerge(SortArray arr){
        arr.isSorting = true;
        arr.comparisons = 0;

        mergesort(arr, 0, arr.arrSize-1);

        arr.sortAnim();
    }
}
