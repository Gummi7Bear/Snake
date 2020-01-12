package snakegame;

import java.awt.event.KeyEvent;

public class Room {
    /**Размеры игрового поля
     */
    private int width, height;

    private Snake snake;
    private Mouse mouse;
    public static Room gameRoom;


    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public static void main(String[] args) {
        gameRoom = new Room(20, 20, new Snake(10, 10));
        gameRoom.snake.setDirection(SnakeDirection.DOWN);
        gameRoom.createMouse();
        gameRoom.run();

    }

    /**Основной цикл программы.
     */
    public void run() {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //пока змея жива
        while (snake.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если равно символу 'q' - выйти из игры.
                if (event.getKeyChar() == 'q') return;

                //Если "стрелка влево" - сдвинуть фигурку влево
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);
                    //Если "стрелка вверх" - сдвинуть фигурку вверх
                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                    //Если "стрелка вниз" - сдвинуть фигурку вниз
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();       //двигаем змею
            print();  //отображаем текущее состояние игры
            sleep();            //пауза между ходами
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    /**Рисует игру на экране
     */
    public void print() {
        //В массив рисуем текущее состояние игровых объектов
        int[][] matrix = new int[height][width];

        //Отрисовываем границы поля массива
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][0] = 5;
                matrix[y][width-1] = 5;
                matrix[0][x] = 5;
                matrix[height-1][x] = 5;
            }
        }

        //Добавляем координаты змеи, проверяя жива она или нет
        for (int i = 1; i < snake.getSnakeSections().size(); i++) {
            matrix[snake.getSnakeSections().get(i).getY()][snake.getSnakeSections().get(i).getX()] = 3;
        }
        matrix[snake.getY()][snake.getX()] = snake.isAlive() ? 1 : 2;

        //Добавляем координаты мыши
        matrix[mouse.getY()][mouse.getX()] = 4;

        //Массив с отображаемыми символами
        String[] symbols = {"   ", " X ", "RIP", " x ", "^_^", " . "};

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(symbols[matrix[y][x]]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();

    }

    /**Создает новую мышь рандомными координатами
     */
    public void createMouse() {
        //Чтобы мышь не появилась на границах проверяем х и y
        int x = (int) (Math.random() * width);
        if(x == 0)
            x++;
        else if(x == width-1)
            x--;

        int y = (int) (Math.random() * height);
        if(y == 0)
            y++;
        else if(y == height-1)
            y--;

        mouse = new Mouse(x, y);

    }

    /**Если мышь съедена, создаем новую мышь
     */
    public void eatMouse() {
         createMouse();
    }

    /**Делает паузу, которая зависит от длины змеи
     */
    public void sleep() {
        try {
        if (snake.getSnakeSections().size() < 11) {
                Thread.sleep(500 - snake.getSnakeSections().size() * 20);
        }

        else if (snake.getSnakeSections().size() < 15){
            Thread.sleep(300 - (snake.getSnakeSections().size()-10) * 25);
        }

        else {
            Thread.sleep(200);
        }
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException  методе sleep()");
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

}
