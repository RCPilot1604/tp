package tutorlink.model.component;

public abstract class Component {
    private String name;
    private double maxScore;
    private double weight;

    public Component(String name, double maxScore, double weight) {
        this.name = name.toLowerCase();
        this.maxScore = maxScore;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Component comp) {
            return comp.getName().equals(this.getName())
                    && comp.getMaxScore() == this.getMaxScore()
                    && comp.getWeight() == this.getWeight();
        }
        return false;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", maxScore=" + maxScore + ", weight=" + weight + "]";
    }
}
