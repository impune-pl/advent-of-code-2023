package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.ArrayList;
import java.util.List;

public class GearRatiosS2 implements AdventTask {
    private static final String NAME = "GearRatiosS2";
    private static final int NUMBER = 6;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getNumber() {
        return NUMBER;
    }

    @Override
    public String run(String input) {
        List<Token> tokens = this.parseInput(input.split("\n"));
        List<Number> numbers = tokens.stream().filter(token -> token.type == TokenType.NUMBER).map(token -> (Number) token).toList();
        List<Symbol> symbols = tokens.stream().filter(token -> token.type == TokenType.SYMBOL).map(token -> (Symbol) token).toList();
        return Integer.valueOf(symbols.stream().mapToInt((symbol) -> this.findAndMultiplyAdjacent(symbol, numbers)).sum()).toString();
    }

    private Integer findAndMultiplyAdjacent(Symbol symbol, List<Number> numbers) {
        int[] values = numbers.stream().filter(number -> {
            for (int i = 0; i < number.length; i++) {
                if (symbol.position.calculateDistance(number.position.x, number.position.y + i) == 1)
                    return true;
            }
            return false;
        }).mapToInt(Number::getValue).toArray();
        if (values.length == 2)
            return values[0] * values[1];
        return 0;
    }

    private boolean isCloseToSymbol(Number number, List<Symbol> symbols) {
        return symbols.stream().anyMatch(symbol -> {
            for (int i = 0; i < number.length; i++) {
                if (symbol.position.calculateDistance(number.position.x, number.position.y + i) == 1)
                    return true;
            }
            return false;
        });
    }

    private List<Token> parseInput(String[] inputLines) {
        List<Token> tokens = new ArrayList<>();
        for (int currentX = 0; currentX < inputLines.length; currentX++) {
            if (inputLines[currentX].matches("^\\.+$")) continue;
            char[] line = inputLines[currentX].toCharArray();
            for (int currentY = 0; currentY < line.length; currentY++) {
                char character = line[currentY];
                if (!((character >= '0' && character <= '9') || (character == '*'))) continue;
                StringBuilder tokenBuilder = new StringBuilder();
                tokens.add(this.constructToken(tokenBuilder, line, currentY, currentX));
                currentY += tokenBuilder.length() - 1;
            }
        }
        return tokens;
    }

    private Token constructToken(StringBuilder tokenBuilder, char[] line, int positionY, int positionX) {
        if (line[positionY] == '*') {
            tokenBuilder.append(line[positionY]);
            return new Symbol(new Point2d(positionX, positionY), tokenBuilder.toString());
        }
        int currentY = positionY;
        while (currentY < line.length && line[currentY] >= '0' && line[currentY] <= '9') {
            tokenBuilder.append(line[currentY]);
            currentY++;
        }
        return new Number(new Point2d(positionX, positionY), tokenBuilder.toString());
    }

    enum TokenType {
        SYMBOL, NUMBER
    }

    abstract class Token {
        final TokenType type;
        final Point2d position;
        final String value;

        public Token(Point2d position, String value, TokenType type) {
            this.position = position;
            this.value = value;
            this.type = type;
        }
    }

    class Symbol extends Token {
        public Symbol(Point2d location, String value) {
            super(location, value, TokenType.SYMBOL);
        }
    }

    class Number extends Token {
        final Integer length;
        final Integer value;

        public Number(Point2d start, String value) {
            super(start, value, TokenType.NUMBER);
            this.length = value.length();
            this.value = Integer.valueOf(value);
        }

        public Integer getValue() {
            return value;
        }
    }

    class Point2d {
        final int x, y;

        public Point2d(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int calculateDistance(int x, int y) {
            return Math.max(Math.abs(x - this.x), Math.abs(y - this.y));
        }
    }
}