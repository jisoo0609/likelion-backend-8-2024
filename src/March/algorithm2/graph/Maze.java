package March.algorithm2.graph;

import java.util.LinkedList;
import java.util.Queue;

public class Maze {
    private static final int[] dy = {0, 0, -1, 1};
    private static final int[] dx = {-1, 1, 0, 0};

    // BFS로 도달하는 최단거리를 구해보자.
    public static void main(String[] args) {
        int[][] maze = new int[][]{
                {0, 0, 0, 0, 0, 3},
                {1, 0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1, 0},
                {1, 0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0, 0},
                {2, 1, 1, 0, 1, 1}
        };

        // BFS를 위한 Queue를 만드는데
        // 2차원 배열의 좌표를 담아야 하기 때문에,
        // int[]을 받는다.
        Queue<int[]> toVisit = new LinkedList<>();
        // 각 칸의 방문 여부를 2차원 boolean 배열로 관리한다.
        boolean[][] visited = new boolean[6][6];
        // 첫번째 방문하는 지점은 2가 있는곳, 즉 5, 0
        // 여기에 얼마나 걸려서 도달했는지에 대한 정보를 포함한다.
        toVisit.add(new int[]{5, 0, 0});
        // 결과 최솟값을 저장하기 위한 변수
        int min = -1;
        // BFS를 진행한다.
        while (!toVisit.isEmpty()) {
            int[] next = toVisit.poll();
            int nowY = next[0];
            int nowX = next[1];
            // 방문했으면 스킵
            if (visited[nowY][nowX]) continue;

            // 방문했다고 기록
            visited[nowY][nowX] = true;
            // 현재까지 걸린 거리를 기록
            int steps = next[2];

            // 만약 도착지점이라면,
            // 얼마나 걸렸는지 기록하고 반복을 종료한다.
            if (maze[nowY][nowX] == 3) {
                min = steps;
                break;
            }

            // 다음에 방문할 곳들을 살펴본다.
            for (int i = 0; i < 4; i++) {
                // 다음에 방문할 곳은 (nextY, nextX)
                int nextY = nowY + dy[i];
                int nextX = nowX + dx[i];
                if (
                    // 미로의 영역 내부에 있는지?
                        checkBounds(nextY, nextX) &&
                                // 벽이 없거나(0) 도착지점(3) (이동 가능한 칸)인지?
                                (maze[nextY][nextX] == 0 || maze[nextY][nextX] == 3) &&
                                // 아직 방문하지 않았는지?
                                !visited[nextY][nextX]
                )
                    toVisit.add(new int[]{nextY, nextX, steps + 1});
            }
        }

        System.out.println(min);
    }

    // 미로의 영역 내부에 있는지?
    private static boolean checkBounds(int y, int x) {
        return
                -1 < x && x < 6 &&
                        -1 < y && y < 6;
    }
}