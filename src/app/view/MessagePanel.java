package app.view;

import app.controller.MessageServer;
import app.model.Message;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class MessagePanel extends JPanel {
    private final JTree serverTree;
    private final ServerTreeCellRenderer treeCellRenderer;
    private ServerTreeCellEditor treeCellEditor;
    private Set<Integer> selectedServers;
    private MessageServer messageServer;

    public MessagePanel() {
        messageServer = new MessageServer();

        selectedServers = new TreeSet<>();
        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);

        treeCellRenderer = new ServerTreeCellRenderer();
        treeCellEditor = new ServerTreeCellEditor();

        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setCellEditor(treeCellEditor);
        serverTree.setEditable(true);



        // lets user select only on node at a time
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        serverTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();

                if (node != null) {
                    Object userObject = node.getUserObject();

                    System.out.println(userObject);
                }
            }
        });

        treeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo info = (ServerInfo) treeCellEditor.getCellEditorValue();

                System.out.println(info + ": " + info.getId() + ": " + info.isChecked());

                int serverId = info.getId();

                if (info.isChecked()) {
                    selectedServers.add(serverId);
                }else {
                    selectedServers.remove(serverId);
                }

                messageServer.setSelectedServers(selectedServers);

                System.out.println("Messages waiting " + messageServer);
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                System.out.println("Hello");
            }
        });

        setLayout(new BorderLayout());

        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }


    private void retrieveMessages() {
        SwingWorker<List<Message>, Integer> worker = new SwingWorker<List<Message>, Integer>() {
            @Override
            protected List<Message> doInBackground() throws Exception {
                List<Message> retrieve = new ArrayList<>();

                int count = 0;

                for (Message message: messageServer) {
                    System.out.println(message.getTitle());

                    count++;

                    publish(count);
                }

                return null;
            }

            /**
             * Receives data chunks from the {@code publish} method asynchronously on the
             * <i>Event Dispatch Thread</i>.
             *
             * <p>
             * Please refer to the {@link #publish} method for more details.
             *
             * @param chunks intermediate results to process
             * @see #publish
             */
            @Override
            protected void process(List<Integer> chunks) {
                int retrieved = chunks.get(chunks.size() - 1);

                System.out.println("Got " + retrieved + " messages.");
            }

            /**
             * Executed on the <i>Event Dispatch Thread</i> after the {@code doInBackground}
             * method is finished. The default
             * implementation does nothing. Subclasses may override this method to
             * perform completion actions on the <i>Event Dispatch Thread</i>. Note
             * that you can query status inside the implementation of this method to
             * determine the result of this task or whether this task has been cancelled.
             *
             * @see #doInBackground
             * @see #isCancelled()
             * @see #get
             */
            @Override
            protected void done() {
                try {
                    List<Message> retrieve = get();
                    System.out.println("Retrieved " + retrieve.size() + " Messages");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0, selectedServers.contains(1)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1, selectedServers.contains(2)));

        branch1.add(server1);
        branch1.add(server2);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3, selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4, selectedServers.contains(4)));

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;
    }
}
