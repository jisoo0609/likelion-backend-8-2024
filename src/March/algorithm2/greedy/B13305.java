// 주유소
// https://www.acmicpc.net/problem/13305
package March.algorithm2.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class B13305 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cityCount = Integer.parseInt(br.readLine());
        // 거리 정보 받아와서 정리하기.
        String[] distInfo = br.readLine().split(" ");
        // cityDistance[i] => i부터 i + 1까지의 거리
        long[] cityDistance = new long[cityCount - 1];
        for (int i = 0; i < cityCount; i++) {
            cityDistance[i] = Long.parseLong(distInfo[i]);
        }

        // 기름값 정보 받아와서 정리하기
        String[] fuelInfo = br.readLine().split(" ");
        long[] cityFuel = new long[cityCount];
        for (int i = 0; i < cityCount; i++) {
            cityFuel[i] = Long.parseLong(fuelInfo[i]);
        }

        // 현재 목표한 도시에서 까지의 가격 중 최소값
        long currentMin = Long.MIN_VALUE;
        // 이 도시까지 오는데 걸린 거리
        long needToGo = 0;
        // 전체 비용
        long totalPrice = 0;
        // N개의 도시가 있지만, N - 1번만 이동한다.
        for (int i = 0; i < cityCount - 1; i++) {
            // 만약 다음 도시가 여태까지 본 기름값 중 제일 싸다면,
            if (cityFuel[i] < currentMin) {
                // 해당 도시까지 가는데 걸리는 거리만큼 지금 제일 싼 비용으로 넣어주자.
                totalPrice += currentMin * needToGo;
                // 기름의 최소값을 갱신한다.
                currentMin = cityFuel[i];
                // 여기서부터 출발하면 최소한 이동해야하는 거리
                needToGo = cityDistance[i];
            }
            // 아니라면
            else {
                // 다음 도시를 살펴보고, 그때 이동해야하는 거리를 추가한다.
                needToGo += cityDistance[i];
            }
        }
        // 마지막에 도착할때까지 걸리는 거리만큼 최소비용으로 추가해준다.
        System.out.println(totalPrice + needToGo * currentMin);
    }
}
