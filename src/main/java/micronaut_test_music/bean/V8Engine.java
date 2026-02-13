package micronaut_test_music.bean;

import jakarta.inject.Singleton;

@Singleton
public class V8Engine implements Engine {
    private int cylinders = 8;

    @Override
    public String start() {
        return "Starting V8";
    }

    @Override
    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }
}
