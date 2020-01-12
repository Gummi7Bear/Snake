package snakegame;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    /**Список частей змеи
     */
    private List<SnakeSection> snakeSections = new ArrayList<>();

    /**Змея жива? False - конец игры
     */
    private boolean isAlive;

    /**Направление движения змеи
     */
    private SnakeDirection direction;

    public Snake(int x, int y) {
        snakeSections = new ArrayList<>();
        //добавляем первую часть змеи - голову с ее координатами
        snakeSections.add(new SnakeSection(x, y));
        isAlive = true;
    }

    public List<SnakeSection> getSnakeSections() {
        return snakeSections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    /**Вернуть координату Х головы змеи
     */
    public int getX() {
        return snakeSections.get(0).getX();
    }

    /**Вернуть координату Y головы змеи
     */
    public  int getY() {
        return snakeSections.get(0).getY();
    }

    /**Движение змеи в 1 шаг
     */
    public void move() {
        //Прекратить движение если змея умерла
        if (!isAlive) return;

        //В зависимости от направления движения вызвать метод move с координатами следующего шага змеи
        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**Изменение координат головы и хвоста змеи за 1 шаг
     */
    public void move(int dx, int dy) {
        //Создаем новую голову
        SnakeSection newSnakeHead = new SnakeSection(getX()+ dx, getY() + dy);

        //Проверяем, не вылезла ли она за границу комнаты (если да, то змея умирает)
        checkBorders(newSnakeHead);

        //Проверяем, не совпадает ли она с уже существующими кусочками змеи (если да, то змея умирает)
        checkBody(newSnakeHead);

        //Если змея поймала мышь (координаты головы совпадают с координатами мыши), то создаем новую мышь и хвост не удаляем.
        if (newSnakeHead.getX() == Room.gameRoom.getMouse().getX() && newSnakeHead.getY() == Room.gameRoom.getMouse().getY()){
            Room.gameRoom.eatMouse();
            snakeSections.add(0, newSnakeHead);
        }
        //Добавляем голову к змее (со стороны головы) и удаляем последний кусочек из хвоста.
        else {
            snakeSections.add(0, newSnakeHead);
            snakeSections.remove(snakeSections.size()-1);
        }
    }

    /**Если голова змеи за границами комнаты - змея умирает
     */
    public void checkBorders(SnakeSection head) {

        if((head.getX() < 1 || head.getX() >= Room.gameRoom.getWidth()-1) || head.getY() < 1 || head.getY() >= Room.gameRoom.getHeight()-1) {
            isAlive = false;
        }
    }

    /**Для определения, не пересекается ли змея сама с собой
     * Если голова змеи пересекается с ее телом - змея умирает
     */
    public void checkBody(SnakeSection head) {
        if(snakeSections.contains(head)) {
            isAlive = false;
        }
    }
}


