package tutorlink.component;

//@@author TrungBui32
public class Component {
    private String name;
    private double maxScore;
    private int weight;

    public Component(String name, double maxScore, int weight) {
        this.name = name;
        this.maxScore = maxScore;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public int getWeight() {
        return weight;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Component comp) {
            return comp.getName().equalsIgnoreCase(this.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " (maxScore: " + maxScore + ", weight: " + weight + "%)";
    }
}
