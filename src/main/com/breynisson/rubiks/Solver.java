package com.breynisson.rubiks;

import java.util.List;

public interface Solver {

    List<Action> solve(CubeModel cubeModel);
}
