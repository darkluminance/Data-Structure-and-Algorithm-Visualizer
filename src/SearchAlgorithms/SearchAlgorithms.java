package SearchAlgorithms;

import java.awt.*;

public class SearchAlgorithms {
    //Linear Search
    public void LinearSearch(Search s){
        s.algo = "linear";
        s.isSearching = true;
        s.IsFound = false;
        s.resetColors();
        s.comparisons = 0;
        for (int i = 0; i<s.arrSize; i++){
            //Check if stop button has been pressed in the last frame
            if (!s.isSearching){
                return;
            }
            s.arrayColor[i] = s.blueColor;
            s.Update();
            s.sleep(s.animSpeed);
            s.increaseComparison();

            if (s.arr[i] == s.numtofind){
                s.arrayColor[i] = s.sortedColor;
                s.IsFound = true;
                s.Update();
                return;
            }


            s.arrayColor[i] = s.pinkColor;
        }
    }

    public void BinarySearch(Search s){
        s.algo = "binary";
        s.isSearching = true;
        s.IsFound = false;
        s.resetColors();
        s.comparisons = 0;

        sort(s);
        s.Update();

        int l = 0;
        int r = s.arrSize-1;
        int m;

        while (l <= r) {
            //Check if stop button has been pressed in the last frame
            if (!s.isSearching){
                return;
            }
            m = l + (r - l) / 2;
            s.arrayColor[l] = s.blueColor;
            s.arrayColor[m] = s.orangeColor;
            s.arrayColor[r] = s.purpleColor;
            s.Update();
            s.sleep(s.animSpeed);
            s.increaseComparison();

            // Check if x is present at mid
            if (s.arr[m] == s.numtofind){
                s.IsFound = true;
                s.arrayColor[m] = s.sortedColor;
                s.Update();
                return;
            }

            // If x greater, ignore left half
            if (s.arr[m] < s.numtofind)
            {
                for (int i = 0; i<=m; i++){
                    s.arrayColor[i] = s.pinkColor;
                }
                l = m + 1;
            }
            else
            {
                for (int i = m; i<=r; i++){
                    s.arrayColor[i] = s.pinkColor;
                }
                r = m - 1;
            }
        }


    }

    public void sort(Search s){
        int len = s.arrSize;
        for(int i=0;i<len-1;i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if(s.arr[j]>s.arr[j+1]){
                    s.swap(j, j+1);
                }
            }
        }
    }

}
