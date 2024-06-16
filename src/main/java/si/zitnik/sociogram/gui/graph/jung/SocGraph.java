package si.zitnik.sociogram.gui.graph.jung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.enums.PersonDrawType;
import si.zitnik.sociogram.gui.graph.GraphUtil;
import si.zitnik.sociogram.util.RunningUtil;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class SocGraph {
	private SparseMultigraph<Vertex, Edge> graph;
	private RunningUtil runningUtil;
	
	//for each edge rank we have set of according edges
	private HashMap<Integer, HashSet<Edge>> posEdges, negEdges;
	private GraphUtil graphUtil; 
	
	public SocGraph(RunningUtil runningUtil, GraphUtil graphUtil){
		this.runningUtil = runningUtil;
		this.graphUtil = graphUtil;
		graph = new SparseMultigraph<Vertex, Edge>();
		posEdges = new HashMap<Integer, HashSet<Edge>>();
		negEdges = new HashMap<Integer, HashSet<Edge>>();
		initGraph();
	}

	private void initGraph() {
		HashSet<Person> personsToDraw = getPersonsToDraw();
		
		posEdges.put(1, new HashSet<Edge>());
		posEdges.put(2, new HashSet<Edge>());
		posEdges.put(3, new HashSet<Edge>());
		negEdges.put(1, new HashSet<Edge>());
		negEdges.put(2, new HashSet<Edge>());
		negEdges.put(3, new HashSet<Edge>());
		
		for (Person person : personsToDraw) {
				if (this.graphUtil.getPersonToDrawType().equals(PersonDrawType.ALL) || 
						this.graphUtil.getPersonToDrawType().equals(PersonDrawType.UNSELECTED)){ 
					this.graph.addVertex(new Vertex(person.getId()));
				}


            if (this.graphUtil.getPersonToDrawType().equals(PersonDrawType.VZAJEMNO_SELECTED_ALL)) {
                initVzajemnoAllDrawTypes(personsToDraw, person);
            } else {
                initStandardDrawTypes(personsToDraw, person);
            }

		}
		
		for (int i = 0; i < 3; i++) {
			addEdges(LikingType.POSITIVE, i+1);
		}
		for (int i = 0; i < 3; i++) {
			addEdges(LikingType.NEGATIVE, i+1);
		}
	}

    private void initVzajemnoAllDrawTypes(HashSet<Person> personsToDraw, Person person) {
        //positive
        for (int i=0; i<person.getPosSelections().size(); i++) {
            if (person.getPosSelections().get(i) != 0){
                Person personFrom = person;
                Person personTo = runningUtil.getPerson(person.getPosSelections().get(i));

                if (personsToDraw.contains(personTo)) {
                    HashSet<Integer> posSelectedPersonIds = runningUtil.getAllSelectedPersonIds(LikingType.POSITIVE, personTo);
                    if (posSelectedPersonIds.contains(personFrom.getId())) {
                        posEdges.get(i+1).add(new Edge(LikingType.POSITIVE, i+1, person.getId(), person.getPosSelections().get(i)));
                    }
                }
            }
        }

        //negative
        for (int i=0; i<person.getNegSelections().size(); i++) {
            if (person.getPosSelections().get(i) != 0) {
                Person personFrom = person;
                Person personTo = runningUtil.getPerson(person.getNegSelections().get(i));

                if (personsToDraw.contains(personTo)) {
                    HashSet<Integer> negSelectedPersonIds = runningUtil.getAllSelectedPersonIds(LikingType.NEGATIVE, personTo);
                    if (negSelectedPersonIds.contains(personFrom.getId())) {
                        negEdges.get(i+1).add(new Edge(LikingType.NEGATIVE, i+1, person.getId(), person.getPosSelections().get(i)));
                    }
                }
            }
        }
    }

    private void initStandardDrawTypes(HashSet<Person> personsToDraw, Person person) {
        //creating edges
        for (int i = 0; i<3; i++){
            //positive
            if (this.graphUtil.getSelection(LikingType.POSITIVE, i+1)){
                if (person.getPosSelections().size() > i && person.getPosSelections().get(i) != 0){
                    Person personFrom = person;
                    Person personTo = runningUtil.getPerson(person.getPosSelections().get(i));

                    if (personsToDraw.contains(personTo)) {
                        if (!this.graphUtil.getPersonToDrawType().equals(PersonDrawType.VZAJEMNO_SELECTED) ||
                                (this.graphUtil.getPersonToDrawType().equals(PersonDrawType.VZAJEMNO_SELECTED) &&
                                        personTo.getPosSelections().get(i) == personFrom.getId()) )
                            posEdges.get(i+1).add(new Edge(LikingType.POSITIVE, i+1, person.getId(), person.getPosSelections().get(i)));
                    }
                }
            }

            //negative
            if (this.graphUtil.getSelection(LikingType.NEGATIVE, i+1)){
                if (person.getPosSelections().size() > i && person.getNegSelections().get(i) != 0){
                    Person personFrom = person;
                    Person personTo = runningUtil.getPerson(person.getNegSelections().get(i));

                    if (personsToDraw.contains(personTo)) {
                        if (!this.graphUtil.getPersonToDrawType().equals(PersonDrawType.VZAJEMNO_SELECTED) ||
                                (this.graphUtil.getPersonToDrawType().equals(PersonDrawType.VZAJEMNO_SELECTED) &&
                                        personTo.getNegSelections().get(i) == personFrom.getId()) )
                            negEdges.get(i+1).add(new Edge(LikingType.NEGATIVE, i+1, person.getId(), person.getNegSelections().get(i)));
                    }
                }
            }
        }
    }

    private HashSet<Person> getPersonsToDraw() {
		HashSet<Person> retVal = new HashSet<Person>();
		switch (this.graphUtil.getPersonToDrawType()) {
			case ALL:
				retVal.addAll(this.runningUtil.getPersons());
				break;
			case SPECIFIC_PERSON:
				Person specificPerson = this.graphUtil.getPersonToDraw();
				retVal.add(specificPerson);
				for (int i = 0; i<3; i++) {
					if (this.graphUtil.getSelection(LikingType.POSITIVE, i+1)){
						int otherPersonRealId = specificPerson.getPosSelections().get(i);
						if (otherPersonRealId != 0) {
							retVal.add(this.runningUtil.getPerson(otherPersonRealId));
						}
					}
					if (this.graphUtil.getSelection(LikingType.NEGATIVE, i+1)){
						int otherPersonRealId = specificPerson.getNegSelections().get(i);
						if (otherPersonRealId != 0) {
							retVal.add(this.runningUtil.getPerson(otherPersonRealId));
						}
					}
				}
				break;
			case UNSELECTED:
				for (Person person : this.runningUtil.getPersons()) {
					int numOfSelected = this.runningUtil.getNumOfSelected(person.getId(), LikingType.POSITIVE) + this.runningUtil.getNumOfSelected(person.getId(), LikingType.NEGATIVE);
					if (numOfSelected == 0){
						retVal.add(person);
					}
				}
				break;
			case VZAJEMNO_SELECTED:
				retVal.addAll(this.runningUtil.getAllVzajemnoSelected());
				break;
            case VZAJEMNO_SELECTED_ALL:
                retVal.addAll(this.runningUtil.getAllVzajemnoSelected());
                break;
		}
		
		if (!graphUtil.isDrawMan()){
			ArrayList<Person> personsNotToDraw = new ArrayList<Person>();
			for (Person person : retVal) {
				if (person.getGender().equals(Gender.MALE)){
					personsNotToDraw.add(person);
				}
			}
			retVal.removeAll(personsNotToDraw);
		}
		if (!graphUtil.isDrawWoman()){
			ArrayList<Person> personsNotToDraw = new ArrayList<Person>();
			for (Person person : retVal) {
				if (person.getGender().equals(Gender.FEMALE)){
					personsNotToDraw.add(person);
				}
			}
			retVal.removeAll(personsNotToDraw);
		}
		
		return retVal;
	}

	public void addEdges(LikingType liking, int rank) {
		if (liking.equals(LikingType.POSITIVE)){
			for (Edge edge : posEdges.get(rank)) {
				this.graph.addEdge(edge, new Vertex(edge.getFromRealId()), new Vertex(edge.getToRealId()), EdgeType.DIRECTED);
			}
		} else {
			for (Edge edge : negEdges.get(rank)) {
				this.graph.addEdge(edge, new Vertex(edge.getFromRealId()), new Vertex(edge.getToRealId()), EdgeType.DIRECTED);
			}
		}
	}
	
	public Graph<Vertex, Edge> getGraph(){
		return this.graph;
	}
	
	

}
