package tutorlink.model.component;

public class Exam extends Component{
    public Exam(String name, double maxScore, double weight) {
        super(name, maxScore, weight);
    }
    @Override
    public String toString() {
        return "Exam " + super.toString();
    }
}
