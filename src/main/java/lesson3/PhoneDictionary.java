package lesson3;

import java.util.*;

public class PhoneDictionary {

    private Set<String> lastNames;
    private Set<String> phoneNumbers;
    private Map<String, List<String>> namePerPhoneNumbers;

    public PhoneDictionary() {
        this.namePerPhoneNumbers = new HashMap<>();
        this.lastNames = new HashSet<>();
        this.phoneNumbers = new HashSet<>();
    }

    public void add(String lastName, String phoneNumber) {
        lastNames.add(lastName);
        phoneNumbers.add(phoneNumber);
        List<String> currentPhoneNumbers = namePerPhoneNumbers.getOrDefault(lastName, new ArrayList<>());
        currentPhoneNumbers.add(phoneNumber);
        namePerPhoneNumbers.put(lastName, currentPhoneNumbers);
    }

    public List<String> get(String lastName) {
        return namePerPhoneNumbers.get(lastName);
    }

}

