package Managers;

import Containers.TodoEntry;
import GUIElements.TaskTile;
import com.google.cloud.firestore.Firestore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class GUIManager {
    public GUIManager(){
        JFrame frame = initialize();
        frame.setContentPane(getMainPanel());
        frame.setVisible(true);


    }

    private JFrame initialize(){
        JFrame frame = new JFrame("Todol");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)d.getWidth()/2,(int)d.getHeight()/2);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private JScrollPane getMainPanel(){
        FirestoreManager manager = new FirestoreManager();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        ArrayList<TodoEntry> data = manager.getAllData();
        data.sort(new Comparator<TodoEntry>() {
            @Override
            public int compare(TodoEntry o1, TodoEntry o2) {
               long timeDiffL = o1.getTaskDay().getTime() - o2.getTaskDay().getTime();
               if(timeDiffL == 0){
                   return (int)(o1.getPriority() - o2.getPriority());
               }
               else return (int)timeDiffL;

            }
        });
        for(TodoEntry entry: data){
            mainPanel.add(new TaskTile(entry));
        }
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }


}