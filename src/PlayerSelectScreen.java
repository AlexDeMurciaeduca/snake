import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//needs massive aesthetic rewrites
public class PlayerSelectScreen extends JPanel {

    JButton go;
    JButton quit;
    MainWindow mw;

    JRadioButton opt1;
    JRadioButton opt2;
    JRadioButton opt3;

    public PlayerSelectScreen(MainWindow mw) {
        this.mw = mw;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addLabel("Select Players: ");
        addLabel("Default: 1Player");

        setupRadioButtons();
        setupButtons();
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        add(label);
    }

    private void setupRadioButtons() {
        opt1 = new JRadioButton("1 Player(Default)");
        opt1.setSelected(true);

        opt2 = new JRadioButton("2 Players");
        opt3 = new JRadioButton("3 Players");

        ButtonGroup grp = new ButtonGroup();
        grp.add(opt1);
        grp.add(opt2);
        grp.add(opt3);

        add(opt1);
        add(opt2);
        add(opt3);

        setupRadioButtonActionListeners();
    }

    private void setupRadioButtonActionListeners() {
        ActionListener radioButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                playerOptions();
            }
        };

        opt1.addActionListener(radioButtonListener);
        opt2.addActionListener(radioButtonListener);
        opt3.addActionListener(radioButtonListener);
    }

    private void setupButtons() {
        go = new JButton("Customize Board");
        quit = new JButton("Back");

        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                goButtonActionListener();
            }
        });

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                quitButtonActionListener();
            }
        });

        add(go);
        add(quit);
    }

    public void quitButtonActionListener() {
        mw.showCard("One");
    }

    public void goButtonActionListener() {
        playerOptions();
        mw.s4.setUpPlayers();
        mw.showCard("Three");
    }

    public void playerOptions() {
        int m = 5;
        if (opt1.isSelected()) {
            m = 1;
        } else if (opt2.isSelected()) {
            m = 2;
        } else if (opt3.isSelected()) {
            m = 3;
        }
        mw.s4.setMaxPlayers(m);
    }
}
