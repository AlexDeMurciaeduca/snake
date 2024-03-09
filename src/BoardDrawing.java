
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BoardDrawing extends JPanel {

    int b = 0;
    int row = 8;
    int col = 8;
    ArrayList<Rectangle> cells;
    int player;
    int[] cellnos;

    BoardScreen bs;
    ArrayList<Portal> portals;
    ArrayList<Player> players;

    // Constantes para los colores utilizados
    private static final Color CELL_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLUE;
    private static final Color SNAKE_COLOR = Color.RED;
    private static final Color LADDER_COLOR = Color.GREEN;

    // Constantes para los mensajes de diálogo
    private static final String LADDER_MESSAGE = "You are up through ladder at position ";
    private static final String SNAKE_MESSAGE = "Snake at ";

    public BoardDrawing(int row, int col, BoardScreen bs) {
        this.bs = bs;

        this.row = row;
        this.col = col;

        cells = new ArrayList<Rectangle>();

        cellnos = new int[row * col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i % 2 == 0) {
                    cellnos[i * col + j] = i * col + j;
                } else {
                    cellnos[i * col + j] = i * col + (row - 1 - j);
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cellnos[i * col + j] = row * col - 1 - cellnos[i * col + j];
            }
        }

        int noPorts = 8;
        bs.portals = new ArrayList<Portal>(noPorts);
        for (int i = 0; i < noPorts; i++) {
            Portal temp = new Portal(row * col);
            bs.portals.add(temp);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / col;
        int cellHeight = height / row;

        int xOffset = (width - (col * cellWidth)) / 2;
        int yOffset = (height - (row * cellHeight)) / 2;

        if (cells.isEmpty()) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Rectangle latest = new Rectangle(
                            xOffset + (j * cellWidth),
                            yOffset + (i * cellHeight),
                            cellWidth,
                            cellHeight);
                    cells.add(latest);
                }
            }
        }

        g2d.setColor(CELL_COLOR);
        for (Rectangle cell : cells) {
            g2d.fill(cell);
        }

        g2d.setColor(BORDER_COLOR);
        for (Rectangle cell : cells) {
            g2d.draw(cell);
        }

        g2d.setColor(BORDER_COLOR);
        int i = 0; // i es nuestra numeración visible
        for (Rectangle cell : cells) {

            String message = "" + cellnos[i];
            g2d.drawString(message, (int) cell.getCenterX(), (int) cell.getCenterY());

            // Dibujar posición del jugador
            for (int pl = 0; pl < bs.maxPlayers; pl++) {
                if (bs.players.get(pl).getPlayerPosn() == cellnos[i]) { // Solo se considera un jugador aquí

                    g2d.setColor(bs.players.get(pl).getPlayerColor()); // Cambiar al color del jugador
                    g2d.fillRect(cell.getLocation().x + pl * cellWidth / 4, cell.getLocation().y, cellWidth / 4, cellHeight / 4); // Cambiar a la posición del jugador
                    g2d.setColor(BORDER_COLOR);
                }
            }

            if (cellnos[i] == row * col - 1) {
                for (int pl = 0; pl < bs.maxPlayers; pl++) {
                    if (bs.players.get(pl).getPlayerPosn() >= cellnos[i]) { // Solo se considera un jugador aquí

                        g2d.setColor(bs.players.get(pl).getPlayerColor()); // Cambiar al color del jugador
                        g2d.fillRect(cell.getLocation().x + pl * cellWidth / 4, cell.getLocation().y, cellWidth / 4, cellHeight / 4); // Cambiar a la posición del jugador
                        g2d.setColor(BORDER_COLOR);
                    }
                }
            }
            i++;
        }

        // Dibujar serpientes y escaleras
        for (Portal port : bs.portals) {
            if (port.returnNature() == -1) {
                g2d.setColor(SNAKE_COLOR);
            } else {
                g2d.setColor(LADDER_COLOR);
            }

            int ind;
            int s = port.returnStart();
            for (ind = 0; ind < row * col; ind++) {
                if (cellnos[ind] == s) {
                    break;
                }
            }

            int j;
            int e = port.returnEnd();
            for (j = 0; j < row * col; j++) {
                if (cellnos[j] == e) {
                    break;
                }
            }

            g2d.drawLine((int) cells.get(ind).getCenterX(), (int) cells.get(ind).getCenterY(), (int) cells.get(j).getCenterX(), (int) cells.get(j).getCenterY());
        }
    }

    public String ensurePlayerPosition(int pnos) {
        String message = "";
        for (Portal port : bs.portals) {
            if (bs.players.get(pnos).getPlayerPosn() == port.returnStart()) {
                bs.players.get(pnos).setPosition(port.returnEnd());
                if (port.returnNature() == 1) {
                    message += LADDER_MESSAGE + port.returnStart();
                } else if (port.returnNature() == -1) {
                    message += SNAKE_MESSAGE + port.returnStart() + " got you.";
                }
            }
        }
        return message;
    }

    public void setPlayer(int a, int pnos) {
        bs.players.get(pnos).incPosition(a);
    }
}
