package March.algorithm2.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class AdjMatrix {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String [] info = br.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);

        // 인접행렬: 정점들의 개수를 n이라고 하면 n * n 2차원 배열로 그래프의 연결 관계를 표현하는 방법
        int[][] adjMat = new int[nodes][nodes];

        // 간선의 개수만큼 반복해서
        for (int i = 0; i < edges; i++) {
            // 간선의 정보를 받는다
            String[] edgeInfo = br.readLine().split(" ");
            // 연결된 두 정점을 찾는다.
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            // 인접 행렬에 인접했다고 기록한다.
            adjMat[leftNode][rightNode] = 1;
            adjMat[rightNode][leftNode] = 1;
        }

        // 결과를 출력한다.
        for (int i = 0; i < nodes; i++) {
            System.out.println(Arrays.toString(adjMat[i]));
        };
    }
}

/*
입력 예제
8 10
0 1
0 2
0 3
1 3
1 4
2 5
3 4
4 7
5 6
6 7
 */