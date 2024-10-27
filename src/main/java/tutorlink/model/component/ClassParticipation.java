package tutorlink.model.component;

public class ClassParticipation extends Component{
    public ClassParticipation (String name, double maxScore, double weight) {
        super(name, maxScore, weight);
    }
    @Override
    public String toString() {
        return "ClassParticipation " + super.toString();
    }
}
