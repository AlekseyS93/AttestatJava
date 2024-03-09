import java.util.*;
import java.util.stream.Collectors;

class PhoneContact {
    private String contactName;
    private ArrayList<Integer> phoneNumber = new ArrayList<>();


    PhoneContact(String name, int num) {
        contactName = name;
        phoneNumber.add(num);
    }

    public int getPhoneNumberCounter() {
        return this.getPhones().size();
    }

    public String toString() {
        return String.format("{%s:%s}", this.contactName, this.phoneNumber.toString());
    }

    public String getName() {
        return this.contactName;
    }

    public ArrayList<Integer> getPhones() {
        return this.phoneNumber;
    }
}

class PhoneBook {
    private int id = 0;
    private static HashMap<Integer, PhoneContact> phoneBook = new HashMap<>();
    public ArrayList<Integer> sortedKeys = new ArrayList<>();

    public void add(String contactName, Integer phoneNum) {
        Boolean[] isExists = {false};
        phoneBook.forEach((k, v) -> {
            String name = v.getName();
            ArrayList<Integer> phones = v.getPhones();
            if (name.equals(contactName)) {
                phones.add(phoneNum);
                isExists[0] = true;
            }
        });
        if (!isExists[0]) {
            phoneBook.put(++this.id, new PhoneContact(contactName, phoneNum));
        }
        this.wasAdded(this.id);
    }

    public static HashMap<Integer, PhoneContact> getPhoneBook() {
        return phoneBook;
    }

    public void wasAdded(int idx) {
        String str = new String(
                String.format("**** User %s with id(%d) has %d numbers",
                        phoneBook.get(idx).getName(),
                        idx,
                        phoneBook.get(idx).getPhoneNumberCounter()
                )
        );
        System.out.println(str);
    }
}

class Printer {
    public static void main(String[] args) {
        String name1;
        String name2;
        int phone1;
        int phone2;

        if (args.length == 0) {
            name1 = "Smirnov";
            name2 = "Sidorov";
            phone1 = 123777;
            phone2 = 888321;

        } else {
            name1 = args[0];
            name2 = args[1];
            phone1 = Integer.parseInt(args[2]);
            phone2 = Integer.parseInt(args[3]);
        }

        PhoneBook myPhoneBook = new PhoneBook();

        myPhoneBook.add(name1, phone1);
        myPhoneBook.add(name1, phone2);
        myPhoneBook.add(name2, phone2);
        myPhoneBook.add(name2, phone1);
        myPhoneBook.add(name2, phone2);

        System.out.println();

        Map<Integer, PhoneContact> pb = PhoneBook.getPhoneBook();
        LinkedHashMap<Integer, PhoneContact> lhm = pb.entrySet().stream().sorted(
                (e1, e2) -> Integer.compare(
                        e2.getValue().getPhoneNumberCounter(),
                        e1.getValue().getPhoneNumberCounter()
                )
        ).collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                )
        );

        for (var item : lhm.entrySet()) {
            System.out.println(item.toString());
        }
    }
}