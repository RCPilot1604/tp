package tutorlink.model.component;

public class Assignment extends Component {
    public Assignment(String name, double maxScore, double weight) {
        super(name, maxScore, weight);
    }

    @Override
    public String toString() {
        return "Assignment " + super.toString();
    }
}
