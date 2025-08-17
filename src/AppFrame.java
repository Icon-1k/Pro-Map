import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class AppFrame extends JFrame implements ActionListener {
    private JLabel startLabel;
    private JLabel stopLabel;
    private JLabel routeLabel;
    private JButton findRouteButton;
    private JButton clearButton;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel resultPanel;
    private JComboBox<String> startComboBox;
    private JComboBox<String> endComboBox;
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    private Graph graph;
    private Map<String, Nodes> locationNodes;

    public AppFrame() {
        initializeGraph();
        setupUI();
    }

    private void initializeGraph() {
        graph = new Graph(true);
        locationNodes = new HashMap<>();

        Nodes engineeringSchool = new Nodes(0, "Engineering School");
        Nodes csDept = new Nodes(1, "CS Department");
        Nodes lawFaculty = new Nodes(2, "Law Faculty");
        Nodes jqb = new Nodes(3, "JQB");
        Nodes mainGate = new Nodes(4, "Main Gate");
        Nodes performingArts = new Nodes(5, "School of Performing Arts");
        Nodes mathDept = new Nodes(6, "Math Department");
        Nodes balmeLibrary = new Nodes(7, "Balme Library");
        Nodes ugcs = new Nodes(8, "UGCS");
        Nodes businessSchool = new Nodes(9, "Business School");
        Nodes voltaHall = new Nodes(10, "Volta Hall");
        Nodes commonwealth = new Nodes(11, "Commonwealth");
        Nodes greatHall = new Nodes(12, "Great Hall");
        Nodes akuafoHall = new Nodes(13, "Akuafo Hall");
        Nodes legonHall = new Nodes(14, "Legon Hall");
        Nodes bushCanteen = new Nodes(15, "Bush Canteen");
        Nodes sarbahPark = new Nodes(16, "Sarbah Park");
        Nodes fireStation = new Nodes(17, "Fire Station");
        Nodes bankingSquare = new Nodes(18, "Banking Square");
        Nodes nightMarket = new Nodes(19, "Night Market");
        Nodes basicSchool = new Nodes(20, "Basic School");
        Nodes diasporaHalls = new Nodes(21, "Diaspora Halls");

        locationNodes.put("Engineering School", engineeringSchool);
        locationNodes.put("CS Department", csDept);
        locationNodes.put("Law Faculty", lawFaculty);
        locationNodes.put("JQB", jqb);
        locationNodes.put("Main Gate", mainGate);
        locationNodes.put("School of Performing Arts", performingArts);
        locationNodes.put("Math Department", mathDept);
        locationNodes.put("Balme Library", balmeLibrary);
        locationNodes.put("UGCS", ugcs);
        locationNodes.put("Business School", businessSchool);
        locationNodes.put("Volta Hall", voltaHall);
        locationNodes.put("Commonwealth", commonwealth);
        locationNodes.put("Great Hall", greatHall);
        locationNodes.put("Akuafo Hall", akuafoHall);
        locationNodes.put("Legon Hall", legonHall);
        locationNodes.put("Bush Canteen", bushCanteen);
        locationNodes.put("Sarbah Park", sarbahPark);
        locationNodes.put("Fire Station", fireStation);
        locationNodes.put("Banking Square", bankingSquare);
        locationNodes.put("Night Market", nightMarket);
        locationNodes.put("Basic School", basicSchool);
        locationNodes.put("Diaspora Halls", diasporaHalls);

        addEdgesToGraph();
    }

    private void addEdgesToGraph() {
        graph.addEdge(locationNodes.get("Engineering School"), locationNodes.get("CS Department"), 270.12);
        graph.addEdge(locationNodes.get("Engineering School"), locationNodes.get("Law Faculty"), 420.88);
        graph.addEdge(locationNodes.get("Engineering School"), locationNodes.get("JQB"), 502.43);
        graph.addEdge(locationNodes.get("CS Department"), locationNodes.get("Law Faculty"), 346.45);
        graph.addEdge(locationNodes.get("Law Faculty"), locationNodes.get("JQB"), 289.39);
        graph.addEdge(locationNodes.get("CS Department"), locationNodes.get("Math Department"), 208.65);
        graph.addEdge(locationNodes.get("Math Department"), locationNodes.get("UGCS"), 653.88);
        graph.addEdge(locationNodes.get("UGCS"), locationNodes.get("Business School"), 407.81);
        graph.addEdge(locationNodes.get("Business School"), locationNodes.get("Volta Hall"), 346.82);
        graph.addEdge(locationNodes.get("Volta Hall"), locationNodes.get("Commonwealth"), 536.69);
        graph.addEdge(locationNodes.get("Commonwealth"), locationNodes.get("Great Hall"), 586.81);
        graph.addEdge(locationNodes.get("Main Gate"), locationNodes.get("School of Performing Arts"), 50.00);
        graph.addEdge(locationNodes.get("School of Performing Arts"), locationNodes.get("Balme Library"), 992.04);
        graph.addEdge(locationNodes.get("UGCS"), locationNodes.get("Balme Library"), 269.71);
        graph.addEdge(locationNodes.get("Balme Library"), locationNodes.get("Akuafo Hall"), 316.59);
        graph.addEdge(locationNodes.get("Balme Library"), locationNodes.get("Commonwealth"), 520);
        graph.addEdge(locationNodes.get("School of Performing Arts"), locationNodes.get("Akuafo Hall"), 701.74);
        graph.addEdge(locationNodes.get("Balme Library"), locationNodes.get("Legon Hall"), 586.81);
        graph.addEdge(locationNodes.get("Legon Hall"), locationNodes.get("Akuafo Hall"), 100);
        graph.addEdge(locationNodes.get("Legon Hall"), locationNodes.get("Basic School"), 1015.00);
        graph.addEdge(locationNodes.get("Legon Hall"), locationNodes.get("Sarbah Park"), 500.00);
        graph.addEdge(locationNodes.get("Akuafo Hall"), locationNodes.get("Sarbah Park"), 200.00);
        graph.addEdge(locationNodes.get("Basic School"), locationNodes.get("Night Market"), 591.36);
        graph.addEdge(locationNodes.get("Night Market"), locationNodes.get("Diaspora Halls"), 645.28);
        graph.addEdge(locationNodes.get("Night Market"), locationNodes.get("Banking Square"), 957.14);
        graph.addEdge(locationNodes.get("Bush Canteen"), locationNodes.get("Fire Station"), 122.85);
        graph.addEdge(locationNodes.get("Fire Station"), locationNodes.get("Banking Square"), 957.14);
        
        graph.addEdge(locationNodes.get("Main Gate"), locationNodes.get("Engineering School"), 800.00);
        graph.addEdge(locationNodes.get("Main Gate"), locationNodes.get("JQB"), 750.00);
        graph.addEdge(locationNodes.get("JQB"), locationNodes.get("Math Department"), 400.00);
        graph.addEdge(locationNodes.get("Great Hall"), locationNodes.get("Akuafo Hall"), 300.00);
        graph.addEdge(locationNodes.get("Great Hall"), locationNodes.get("Legon Hall"), 400.00);
        graph.addEdge(locationNodes.get("Sarbah Park"), locationNodes.get("Bush Canteen"), 350.00);
        graph.addEdge(locationNodes.get("Diaspora Halls"), locationNodes.get("Basic School"), 800.00);
        graph.addEdge(locationNodes.get("Banking Square"), locationNodes.get("Bush Canteen"), 600.00);
    }

    private void setupUI() {
        frame = new JFrame("UG Navigate - Campus Route Finder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputPanel = createInputPanel();
        resultPanel = createResultPanel();

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Route Selection"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] locations = {"Select a location", "Engineering School", "CS Department", "Math Department", 
                             "Law Faculty", "JQB", "Main Gate", "School of Performing Arts", "Balme Library", 
                             "UGCS", "Business School", "Volta Hall", "Commonwealth", "Great Hall", 
                             "Akuafo Hall", "Legon Hall", "Bush Canteen", "Sarbah Park", "Fire Station", 
                             "Banking Square", "Night Market", "Basic School", "Diaspora Halls"};

        startLabel = new JLabel("Starting Location:");
        startLabel.setFont(new Font("Arial", Font.BOLD, 14));
        startComboBox = new JComboBox<>(locations);
        startComboBox.setPreferredSize(new Dimension(250, 30));

        stopLabel = new JLabel("Destination:");
        stopLabel.setFont(new Font("Arial", Font.BOLD, 14));
        endComboBox = new JComboBox<>(locations);
        endComboBox.setPreferredSize(new Dimension(250, 30));

        findRouteButton = new JButton("Find Optimal Route");
        findRouteButton.setFont(new Font("Arial", Font.BOLD, 14));
        findRouteButton.setBackground(new Color(34, 139, 34));
        findRouteButton.setForeground(Color.WHITE);
        findRouteButton.setBorder(BorderFactory.createRaisedBevelBorder());
        findRouteButton.setPreferredSize(new Dimension(150, 35));
        findRouteButton.setFocusPainted(false);

        clearButton = new JButton("Clear Results");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(139, 0, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
        clearButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(startLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(startComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(stopLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(endComboBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(findRouteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonPanel, gbc);

        findRouteButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        addButtonHoverEffects();

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Route Analysis Results"));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(700, 400));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findRouteButton) {
            findRoute();
        } else if (e.getSource() == clearButton) {
            clearResults();
        }
    }

    private void findRoute() {
        try {
            String startLocation = startComboBox.getSelectedItem().toString();
            String endLocation = endComboBox.getSelectedItem().toString();

            if (startLocation.equals("Select a location") || endLocation.equals("Select a location")) {
                JOptionPane.showMessageDialog(frame, "Please select both starting and destination locations.", 
                                            "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (startLocation.equals(endLocation)) {
                JOptionPane.showMessageDialog(frame, "Starting and destination locations must be different.", 
                                            "Invalid Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Nodes startNode = locationNodes.get(startLocation);
            Nodes endNode = locationNodes.get(endLocation);

            if (startNode == null || endNode == null) {
                JOptionPane.showMessageDialog(frame, "Invalid location selected.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            RouteOptimizer.RouteAnalysis analysis = RouteOptimizer.findOptimalRoutes(graph, startNode, endNode, new ArrayList<>());
            displayResults(analysis, startLocation, endLocation);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "An error occurred while finding the route: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void displayResults(RouteOptimizer.RouteAnalysis analysis, String startLocation, String endLocation) {
        StringBuilder result = new StringBuilder();
        result.append("=== UG CAMPUS ROUTE ANALYSIS ===\n");
        result.append("From: ").append(startLocation).append("\n");
        result.append("To: ").append(endLocation).append("\n\n");

        if (analysis.optimalRoute != null) {
            result.append("🎯 OPTIMAL ROUTE:\n");
            result.append("Algorithm: ").append(analysis.optimalRoute.algorithm).append("\n");
            result.append("Path: ").append(String.join(" → ", analysis.optimalRoute.path)).append("\n");
            result.append("Distance: ").append(String.format("%.2f", analysis.optimalRoute.distance)).append(" meters\n");
            result.append("Estimated Time: ").append(String.format("%.1f", analysis.optimalRoute.time)).append(" seconds\n");
            result.append("Estimated Time: ").append(String.format("%.1f", analysis.optimalRoute.time / 60)).append(" minutes\n\n");
        } else {
            result.append("❌ No route found between the selected locations.\n\n");
        }

        if (!analysis.routes.isEmpty()) {
            result.append("🔄 ALTERNATIVE ROUTES:\n");
            for (int i = 0; i < Math.min(3, analysis.routes.size()); i++) {
                SortingAlgorithms.Route route = analysis.routes.get(i);
                result.append(i + 1).append(". ").append(route.algorithm).append(":\n");
                result.append("   Path: ").append(String.join(" → ", route.path)).append("\n");
                result.append("   Distance: ").append(String.format("%.2f", route.distance)).append("m\n");
                result.append("   Time: ").append(String.format("%.1f", route.time)).append("s\n\n");
            }
        }

        if (!analysis.algorithmPerformance.isEmpty()) {
            result.append("⚡ ALGORITHM PERFORMANCE:\n");
            for (Map.Entry<String, Double> entry : analysis.algorithmPerformance.entrySet()) {
                result.append(entry.getKey()).append(": ").append(entry.getValue()).append("ms\n");
            }
            result.append("\n");
        }

        if (analysis.trafficFactor != 1.0) {
            result.append("🚦 TRAFFIC FACTOR: ").append(analysis.trafficFactor).append("x\n\n");
        }

        result.append("📍 CAMPUS LANDMARKS NEARBY:\n");
        if (analysis.optimalRoute != null) {
            for (String location : analysis.optimalRoute.path) {
                if (location.contains("Library") || location.contains("Canteen") || 
                    location.contains("Park") || location.contains("Bank") || 
                    location.contains("Market") || location.contains("Station")) {
                    result.append("• ").append(location).append("\n");
                }
            }
        }

        resultArea.setText(result.toString());
        resultArea.setCaretPosition(0);
    }

    private void clearResults() {
        resultArea.setText("");
        startComboBox.setSelectedIndex(0);
        endComboBox.setSelectedIndex(0);
    }
    
    private void addButtonHoverEffects() {
        findRouteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                findRouteButton.setBackground(new Color(0, 100, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                findRouteButton.setBackground(new Color(34, 139, 34));
            }
        });
        
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(100, 0, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(139, 0, 0));
            }
        });
    }
}