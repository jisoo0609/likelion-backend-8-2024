// 회의실 배정
// https://www.acmicpc.net/problem/1931
package March.algorithm2.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class B1931 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 몇 개의 회의가 있는지
        int meetingCount = Integer.parseInt(br.readLine());
        // 회의에 대한 정보를 저장하기 위한 2차원 배열
        int[][] meetings = new int[meetingCount][2];
        // 회의 정보를 가져온다.
        for (int i = 0; i < meetingCount; i++) {
            String[] meetingInfo = br.readLine().split(" ");
            // 시작 시간
            meetings[i][0] = Integer.parseInt(meetingInfo[0]);
            // 종료 시간
            meetings[i][1] = Integer.parseInt(meetingInfo[1]);
        }

        // 종료시간을 기준으로 오름차순 정렬한다.
        Arrays.sort(meetings, (o1, o2) -> {
            // o1, o2는 meeting[i]와 meeting[j]를 나타낸다. (int[]{시작, 끝})
            if (o1[1] != o2[1]) return o1[1] - o2[1];
            return o1[0] - o2[0];
        });

        // 얼마나 많은 회의를 진행할 수 있는지
        int answer = 0;
        // 현재 가장 빨리 끝나는 회의의 시간을 기록하기 위한 변수
        int lastEnd = 0;
        for (int i = 0; i < meetingCount; i++) {
            // 이번 회의의 시작시간이 이전 회의의 끝 시간보다 늦거나 같으면
            if (meetings[i][0] >= lastEnd) {
                // 진행 가능한 회의가 하나 늘고,
                answer++;
                // 다음 회의가 끝나는 시간을 기록한다.
                lastEnd = meetings[i][1];
            }
        }
        System.out.println(answer);
    }
}
