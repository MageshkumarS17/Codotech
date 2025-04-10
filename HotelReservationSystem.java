import java.awt.*;
import java.util.*;
import javax.swing.*;

class Room {
    int roomNumber;
    String type;
    double price;
    boolean isAvailable = true;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - $" + price;
    }
}

class Reservation {
    String guestName;
    String checkInDate;
    String checkOutDate;
    Room room;

    public Reservation(String guestName, String checkInDate, String checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        room.isAvailable = false;
    }

    @Override
    public String toString() {
        return guestName + " | " + room + " | " + checkInDate + " to " + checkOutDate;
    }
}

public class HotelReservationSystem extends JFrame {
    DefaultListModel<Room> availableRoomsModel = new DefaultListModel<>();
    DefaultListModel<Reservation> reservationsModel = new DefaultListModel<>();
    java.util.List<Room> allRooms = new ArrayList<>();
    java.util.List<Reservation> reservations = new ArrayList<>();

    public HotelReservationSystem() {
        setTitle("Hotel Reservation System");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Room Panel
        JList<Room> roomList = new JList<>(availableRoomsModel);
        JButton reserveBtn = new JButton("Reserve Room");
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomPanel.add(new JLabel("Available Rooms", SwingConstants.CENTER), BorderLayout.NORTH);
        roomPanel.add(new JScrollPane(roomList), BorderLayout.CENTER);
        roomPanel.add(reserveBtn, BorderLayout.SOUTH);

        // Reservation Panel
        JList<Reservation> reservationList = new JList<>(reservationsModel);
        JButton cancelBtn = new JButton("Cancel Reservation");
        JPanel resPanel = new JPanel(new BorderLayout());
        resPanel.add(new JLabel("Reservations", SwingConstants.CENTER), BorderLayout.NORTH);
        resPanel.add(new JScrollPane(reservationList), BorderLayout.CENTER);
        resPanel.add(cancelBtn, BorderLayout.SOUTH);

        add(roomPanel);
        add(resPanel);

        // Sample Rooms
        addRoom(new Room(101, "Single", 99.99));
        addRoom(new Room(102, "Double", 149.99));
        addRoom(new Room(103, "Single", 89.99));
        addRoom(new Room(104, "Suite", 199.99));
        addRoom(new Room(105, "Suite", 209.99));

        // Reserve button
        reserveBtn.addActionListener(e -> {
            Room selected = roomList.getSelectedValue();
            if (selected == null || !selected.isAvailable) return;

            JTextField nameField = new JTextField();
            JTextField checkInField = new JTextField();
            JTextField checkOutField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Guest Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
            panel.add(checkInField);
            panel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
            panel.add(checkOutField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Reservation res = new Reservation(nameField.getText(), checkInField.getText(), checkOutField.getText(), selected);
                reservations.add(res);
                reservationsModel.addElement(res);
                updateRoomList();
                JOptionPane.showMessageDialog(this, "Booking Confirmed! Total Price: $" + selected.price);
            }
        });

        // Cancel button
        cancelBtn.addActionListener(e -> {
            Reservation selected = reservationList.getSelectedValue();
            if (selected == null) return;

            selected.room.isAvailable = true;
            reservations.remove(selected);
            reservationsModel.removeElement(selected);
            updateRoomList();
            JOptionPane.showMessageDialog(this, "Reservation cancelled.");
        });

        setVisible(true);
    }

    private void addRoom(Room room) {
        allRooms.add(room);
        if (room.isAvailable) {
            availableRoomsModel.addElement(room);
        }
    }

    private void updateRoomList() {
        availableRoomsModel.clear();
        for (Room r : allRooms) {
            if (r.isAvailable) {
                availableRoomsModel.addElement(r);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelReservationSystem::new);
    }
}
