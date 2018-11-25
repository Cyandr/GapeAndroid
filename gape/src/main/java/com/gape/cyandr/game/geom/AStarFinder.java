package com.gape.cyandr.game.geom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStarFinder {
    private final static int[] XMove = {0, -1, 0, 1, -1, -1, 1, 1};
    private final static int[] YMove = {-1, 0, 1, 0, 1, -1, -1, 1};
    public ArrayList<Node> OpenList = new ArrayList<AStarFinder.Node>();
    public ArrayList<Node> CloseList = new ArrayList<AStarFinder.Node>();

    public ArrayList<Node> find(int[][] map, int sx, int sy, int ex, int ey, int[] isPass) {
        Node start = new Node(sx, sy);
        Node end = new Node(ex, ey);
        Node node = start;
        CloseList.add(node);
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < 4; i++) {
                int fx = node.x + XMove[i];
                int fy = node.y + YMove[i];
                if (fx >= map.length && fy >= map[0].length) {
                    continue;
                }
                if (end.equals(fx, fy)) {
                    end.parent = node;
                    flag = false;
                    break;
                }
                if (containClose(fx, fy)) {
                    continue;
                }
                if (containOpen(fx, fy)) {
                    Node node3 = getOpen(fx, fy);
                    if (node.G + 10 < node3.G) {
                        node3.parent = node;
                        node3.G = node.G + 10;
                        node3.F = node3.G + node3.H;
                    }
                    continue;
                }
                if (Arrays.binarySearch(isPass, map[fy][fx]) >= 0) {
                    Node node2 = new Node(fx, fy);
                    node2.parent = node;
                    node2.G = node.G + 10;
                    //����manhattan�����㷨  �����е�ֱ�� ����
                    node2.H = Math.abs((ex - fx + ey - fy) * 10);
                    node2.F = node2.G + node2.H;
                    OpenList.add(node2);
                }
            }
            if (flag == false) {
                break;
            }
            if (OpenList.size() == 0) {
                return null;
            }
            node = MinF(OpenList);
            OpenList.remove(node);
            CloseList.add(node);

        }
        ArrayList<Node> Path = new ArrayList<AStarFinder.Node>();
        node = end;
        while (node.parent != null) {
            Path.add(node);
            node = node.parent;
        }
        return Path;
    }

    public int Manhattan() {
        return 0;
    }

    public Node MinF(List<Node> list) {
        Node min = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).F <= min.F) {
                min = list.get(i);
            }
        }
        return min;
    }

    public boolean containOpen(int x, int y) {
        for (Node node : OpenList) {
            if (node.equals(x, y)) {
                return true;
            }
        }
        return false;
    }

    public Node getOpen(int x, int y) {
        for (Node node : OpenList) {
            if (node.equals(x, y)) {
                return node;
            }
        }
        return null;
    }

    public boolean containClose(int x, int y) {
        for (Node node : CloseList) {
            if (node.equals(x, y)) {
                return true;
            }
        }
        return false;
    }

    public class Node {
        public int x, y;
        public Node parent;
        public int G;
        public int H;
        public int F;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(int x, int y) {
            return this.x == x && this.y == y;
        }
    }
}
