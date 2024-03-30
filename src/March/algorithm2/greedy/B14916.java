// 거스름돈
// https://www.acmicpc.net/problem/14916
package March.algorithm2.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B14916 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 거스름돈
        int change = Integer.parseInt(br.readLine());
        // 거슬러 줄 수 없는 경우
        if (change == 1 || change == 3) System.out.println(-1);
        // 5짜리로 다 거슬러 주면 짝수 금액이 남는 경우
        else if ((change % 5) % 2 == 0) {
            // 5짜리로 쓸 수 있는 만큼
            int fives = change / 5;
            // 남은걸 2로
            int twos = (change % 5) / 2;
            System.out.println(fives + twos);
        }
        // 5짜리로 다 거슬러 주면 홀수 금액이 남는 경우
        else {
            // 5짤리로 쓸 수 있는 만큼 -1 (해야 남는 돈을 2원으로 거스를 수 있음)
            int fives = change / 5 - 1;
            // 남은걸 2로
            int twos = ((change % 5) + 5) / 2;
            System.out.println(fives + twos);
        }

    }
}
