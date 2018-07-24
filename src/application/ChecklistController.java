package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ChecklistController implements Initializable {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtName;

	@FXML
	private ToggleGroup tgAge;

	@FXML
	private CheckBox ckTerms;

	@FXML
	private TableView<Person> tableView = new TableView<Person>();

	@FXML
	private TableColumn<Person, String> columnName;

	@FXML
	private TableColumn<Person, String> columnAge;

	@FXML
	private void send() {
		if(validateFields(txtName, tgAge, ckTerms)){			
			Person person = new Person(txtName.getText(), checkAge(tgAge));
			tableView.getItems().add(person);
			
			txtName.clear();
			tgAge.selectToggle(null);
			ckTerms.setSelected(false);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		columnAge.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));

		// Allow the name cell to be editable
		tableView.setEditable(true);
		columnName.setCellFactory(TextFieldTableCell.forTableColumn());

		// This method will allow the table to select multiple rows at once
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

	// Method that get the age value.
	private String checkAge(ToggleGroup age) {
		RadioButton valueAge = (RadioButton) age.getSelectedToggle();
		String ageTxt = valueAge.getText();
		return ageTxt; 
	}

	// Method that will allow the user to double click on a cell and update the name
	// of the person
	public void editName(CellEditEvent edditedCell){
		Person personSelected = tableView.getSelectionModel().getSelectedItem();
		personSelected.setName(edditedCell.getNewValue().toString());
	}

	// Method that delete the selected row(s) from the table.
	public void delete() {
		ObservableList<Person> selectedRows, allPeople;
		allPeople = tableView.getItems();

		// this give us the rows that were selected
		selectedRows = tableView.getSelectionModel().getSelectedItems();

		// this remove the selected row(s).
		allPeople.removeAll(selectedRows);
	}

	// Method that validate if the fields are empty.
	public boolean validateFields(TextField tf, ToggleGroup tg, CheckBox cb) {

		if (tf.getText().equals(null) || tf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "The name can`t be empty.",
					"Terms and Conditions", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if (tgAge.getSelectedToggle() == null) {
			JOptionPane.showMessageDialog(null, "You need to specify your age.",
					"Terms and Conditions", JOptionPane.WARNING_MESSAGE);			
			return false;
		}
		
		if (!cb.isSelected()) {
			JOptionPane.showMessageDialog(null, "To proceed you must accept the terms and conditions.",
											"Terms and Conditions", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	// Method that save the list
	int fileCount;
	public void saveList() {
		try {
			File file = new File("list"+fileCount+".txt");
			fileCount++;
			BufferedWriter bf = new BufferedWriter(new FileWriter(file));

			for (Person person : tableView.getItems()) {
				bf.write("Name: " + person.getName().toString() + " - Age: " + person.getAge().toString() + "\n");
			}

			bf.flush();
			bf.close();
			JOptionPane.showMessageDialog(null, "The list was saved successfully.", "Terms and Conditions",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception error) {
			JOptionPane.showMessageDialog(null, "It was not possible to save the current list." + error.getMessage());
		}
	}
	
	//Method that clear the list.
	public void clearTableView() {
		tableView.getItems().clear();
	}
}
