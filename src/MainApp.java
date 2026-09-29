import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainApp extends JFrame {
    // Gọi Singleton Manager
    private TrainManager manager = TrainManager.getInstance();
    
    // Khai báo các biến giao diện
    private JTable scheduleTable;
    private JTable busTable;
    private JTextArea logArea;

    public MainApp() {
        // 1. Cài đặt khung cửa sổ (Window Setup)
        setTitle("Hệ Thống Quản Lý Vận Tải (Tàu & Bus)");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2. Tạo phần Tiêu đề
        JLabel title = new JLabel("Transport Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // 3. Tạo các Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lịch Trình Tàu", createTrainSchedulePanel());
        tabbedPane.addTab("Tuyến Xe Buýt", createBusListPanel());
        tabbedPane.addTab("Quầy Đặt Vé", createBookingPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // 4. KHỞI TẠO LOG AREA (QUAN TRỌNG: Phải làm bước này trước khi nạp dữ liệu)
        logArea = new JTextArea(6, 50);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // ========================================================================
        // 5. NẠP DỮ LIỆU & HIỂN THỊ (QUAN TRỌNG: Phải để cuối cùng)
        // ========================================================================
        // Lúc này logArea đã được tạo ở bước 4, nên gọi hàm này sẽ KHÔNG bị lỗi nữa
        initMockData(); 
        
        // Cập nhật dữ liệu lên bảng
        refreshTables();
    }

    // --- CÁC HÀM TẠO GIAO DIỆN CON (PANELS) ---

    private JPanel createTrainSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Mã Lịch", "Tàu", "Lộ Trình", "Giờ Đi", "Ghế Tổng"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        scheduleTable = new JTable(model);
        panel.add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBusListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Mã Bus", "Từ Trạm", "Đến Điểm (Destination)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        busTable = new JTable(model);
        panel.add(new JScrollPane(busTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel container = new JPanel(new GridLayout(1, 2));

        // Panel Trái: Đặt Vé Tàu
        JPanel trainPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        trainPanel.setBorder(BorderFactory.createTitledBorder("Đặt Vé Tàu Hỏa"));
        
        JTextField txtSchId = new JTextField("S01");
        JTextField txtCarId = new JTextField("C1");
        JTextField txtSeatId = new JTextField("C1-S1");
        JButton btnBookTrain = new JButton("Đặt Tàu");

        trainPanel.add(new JLabel("Mã Lịch:")); trainPanel.add(txtSchId);
        trainPanel.add(new JLabel("Toa:")); trainPanel.add(txtCarId);
        trainPanel.add(new JLabel("Ghế:")); trainPanel.add(txtSeatId);
        trainPanel.add(new JLabel("")); trainPanel.add(btnBookTrain);

        btnBookTrain.addActionListener(e -> {
            String res = manager.bookTrainTicket(txtSchId.getText(), txtCarId.getText(), txtSeatId.getText(), 150.0);
            logArea.append(res + "\n");
        });

        // Panel Phải: Đặt Vé Bus
        JPanel busPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        busPanel.setBorder(BorderFactory.createTitledBorder("Đặt Vé Xe Buýt"));

        JTextField txtBusId = new JTextField("B01");
        JTextField txtDate = new JTextField(LocalDate.now().toString());
        JButton btnBookBus = new JButton("Đặt Bus");

        busPanel.add(new JLabel("Mã Bus:")); busPanel.add(txtBusId);
        busPanel.add(new JLabel("Ngày đi:")); busPanel.add(txtDate);
        busPanel.add(new JLabel("")); busPanel.add(new JLabel(""));
        busPanel.add(new JLabel("")); busPanel.add(btnBookBus);

        btnBookBus.addActionListener(e -> {
            try {
                String res = manager.bookBusTicket(txtBusId.getText(), LocalDate.parse(txtDate.getText()), 5.0);
                logArea.append(res + "\n");
            } catch (Exception ex) {
                logArea.append("Lỗi định dạng ngày (YYYY-MM-DD)!\n");
            }
        });

        container.add(trainPanel);
        container.add(busPanel);
        return container;
    }

    // --- CÁC HÀM HỖ TRỢ ---

    private void refreshTables() {
        // Load Tàu
        DefaultTableModel trainModel = (DefaultTableModel) scheduleTable.getModel();
        trainModel.setRowCount(0);
        for (ScheduleDetail s : manager.getAllSchedules().values()) {
            trainModel.addRow(new Object[]{
                s.getId(), s.getTrain().getName(), s.getRoute().getName(), 
                s.getDepartureTime(), s.getTrain().getTotalSeats()
            });
        }

        // Load Bus
        DefaultTableModel busModel = (DefaultTableModel) busTable.getModel();
        busModel.setRowCount(0);
        for (Bus b : manager.getAllBuses().values()) {
            busModel.addRow(new Object[]{
                b.getId(), b.getBeginStation().getName(), b.getBusStop().getName()
            });
        }
    }

    private void initMockData() {
        try {
            // 1. Tạo Trạm
            Station sHN = new Station("HN", "Ga Hà Nội");
            Station sDN = new Station("DN", "Ga Đà Nẵng");
            Station sHCM = new Station("HCM", "Ga Sài Gòn");

            // 2. Tạo Tuyến Tàu
            Route route = new Route("R1", "Bắc - Nam");
            route.addRoutePart(new RoutePart(sHN, sDN, 700));
            route.addRoutePart(new RoutePart(sDN, sHCM, 900));

            Locomotive loco = new Locomotive("L1", 100.0);
            Train train = new Train("SE1", loco);
            train.addCarriage(new Carriage("C1", 30));
            manager.addTrain(train);
            manager.addSchedule(new ScheduleDetail("S01", LocalDateTime.now().plusHours(2), train, route));

            // 3. Tạo Tuyến Bus
            Destination dAirport = new Destination("Sân bay Nội Bài", "Sóc Sơn, HN");
            Destination dHoiAn = new Destination("Phố cổ Hội An", "Quảng Nam");

            Bus b1 = new Bus("B01", sHN, dAirport);
            Bus b2 = new Bus("B02", sDN, dHoiAn);

            manager.addBus(b1);
            manager.addBus(b2);

            // Ghi log (Nếu logArea chưa new thì dòng này sẽ gây lỗi)
            logArea.append("=== KHỞI TẠO DỮ LIỆU THÀNH CÔNG ===\n");
            logArea.append("- Đã tạo tàu SE1\n");
            logArea.append("- Đã tạo Bus B01, B02\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}