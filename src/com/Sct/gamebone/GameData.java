package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Rect;

public class GameData {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public int cols = 30;
	public int rows = 15;
	public int[] margin = new int[] { 50, 30, 20, 30 };
	public List<Integer> snake = new ArrayList<Integer>();
	public int size = -1;
	public int direction = LEFT;
	public int speed = 100;

	public int doorId = -1;
	public int doorDirection = -1;

	public List<Integer> obstacle = new ArrayList<Integer>();

	public void initData(int level) {
		updateSize();
		for (int i = 10; i >= 0; i--) {
			snake.add(i * cols);
		}
		int row = 5;
		for (int i = 7; i < cols - 7; i++) {
			obstacle.add((row - 1) * cols + i);
			obstacle.add((rows - row - 1) * cols + i);
		}
		GenerateNewDoor();
	}

	public Rect getRect(int id) {
		int size = getSize();

		int c = id % cols;
		int r = id / cols;

		int x = margin[3] + c * size;
		int y = margin[0] + r * size;
		return new Rect(x, y, x + size, y + size);
	}

	public int getSize() {
		return size;
	}

	public void updateSize() {
		int height = GameApp.getApplication().getScreenHeight();
		int width = GameApp.getApplication().getScreenWidth();
		int rectWidth = width - margin[1] - margin[3];
		int rectHeight = height - margin[0] - margin[2];

		int aveWidth = rectWidth / cols;
		int aveHeight = rectHeight / rows;
		size = aveWidth < aveHeight ? aveWidth : aveHeight;
	}

	public void MoveRight() {
		if (direction != RIGHT)
			return;
		MoveBody();
		int headId = snake.get(0);
		if ((headId + 1) % cols == 0) {
			snake.set(0, headId + 1 - cols);
		} else {
			snake.set(0, headId + 1);
		}
	}

	public void MoveLeft() {
		if (direction != LEFT)
			return;
		MoveBody();
		int headId = snake.get(0);
		if (headId % cols == 0) {
			snake.set(0, headId + cols - 1);
		} else {
			snake.set(0, headId - 1);
		}

	}

	public void MoveDown() {
		if (direction != DOWN)
			return;
		MoveBody();
		int headId = snake.get(0);
		if (headId / cols == rows - 1) {
			snake.set(0, headId % cols);
		} else {
			snake.set(0, headId + cols);
		}
	}

	public void MoveUp() {
		if (direction != UP)
			return;
		MoveBody();
		int headId = snake.get(0);
		if (headId / cols == 0) {
			snake.set(0, cols * (rows - 1) + headId);
		} else {
			snake.set(0, headId - cols);
		}
	}

	public void MoveBody() {
		for (int i = snake.size() - 1; i > 0; i--) {
			snake.set(i, snake.get(i - 1));
		}
	}

	public void Move() {
		switch (direction) {
		case RIGHT:
			MoveRight();
			break;
		case LEFT:
			MoveLeft();
			break;
		case UP:
			MoveUp();
			break;
		case DOWN:
			MoveDown();
			break;
		}
	}

	public void Turn(int d) {
		if (d == LEFT) {
			direction = (direction + 4 - 1) % 4;
		}
		if (d == RIGHT) {
			direction = (direction + 1) % 4;
		}
	}

	public void GenerateNewDoor() {
		Random r = new Random();
		int sum = cols * rows;
		int n = r.nextInt(sum);
		while (snake.contains(n) || obstacle.contains(n)) {
			n = r.nextInt(sum);
		}
		doorId = n;
		doorDirection = r.nextInt(4);
	}

	public boolean checkIsReached() {
		if (snake.get(0) == doorId) {
			if ((direction == UP && doorDirection == DOWN)
					|| (direction == DOWN && doorDirection == UP)
					|| (direction == LEFT && doorDirection == RIGHT)
					|| (direction == RIGHT && doorDirection == LEFT)) {
				GenerateNewDoor();
				return true;
			}
		}
		return false;
	}
}
