package org.example;

import org.example.entities.Pet;
import org.example.entities.User;

public final class Autosaver implements Runnable {
    private final ClientApplication app;
    private final long timeInterval;

    public Autosaver(ClientApplication app, long minutesBetweenSaves) {
        this.app = app;
        this.timeInterval = minutesBetweenSaves * 60 * 1000;
    }

    @Override
    public void run() {
        User currentUser;
        Pet currentPet;
        while (true) {
            try {
                Thread.sleep(timeInterval);
                synchronized (app) {
                    currentUser = app.getCurrentUser();
                    currentPet = app.getCurrentPet();
                }
                if (currentUser == null || currentPet == null) {
                    continue;
                }
                Result<Void> saveResult = ServiceCaller.savePet(currentPet.getId(),
                        currentPet.getHunger(),
                        currentPet.getHappiness(),
                        currentPet.getCleanness(),
                        currentUser.getScore());
                if (saveResult.isSuccess()) {
                    System.out.println("Autosaver thread saved the game successfully.");
                } else {
                    System.out.println("Autosaver thread failed to save the game: " + saveResult.getError());
                }
            } catch (InterruptedException e) {
                System.out.println("Autosaver thread was interrupted.");
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.out.println("Autosaver thread encountered an error: " + e.getMessage());
            }
        }
    }
}
