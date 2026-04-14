package info.zamojski.soft.towercollector.uploader;

import org.checkerframework.checker.index.qual.NonNegative;

public class PNLCheckerSmokeTest{

    void bad() {
        int[] arr = new int[5];

        @NonNegative int i = 5;
        int x = arr[i];   // should fail: valid indices are 0..4
    }
}