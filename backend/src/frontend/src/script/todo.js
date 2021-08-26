import axios from 'axios'

export default {
  name: 'TodoApp',
  data(){
    const defaultData = [
                          { idTodo: 0, titel: "ToDo-App", description: 'Create a todo-App for the JEE module', dueDate: '04.09.2021',isDone : false, actions : '', edit : 'Edit' },
                          { idTodo: 1, titel: "VueJS", description: 'Find information, read about usage, try it yourself ', dueDate: '14.08.2021',isDone : false, actions : '',edit : 'Edit'},
                          { idTodo: 2, titel: "Spring Boot", description: 'Find information, read about usage, try it yourself ', dueDate: '14.08.2021',isDone : false,actions : '',edit : 'Edit'}
                        ];
    
    // Zuweisung von dem Wert funktioniert nicht ...
    var getDate = new Date();
    var newDay = getDate.getFullYear();
    if ((getDate.getMonth()+1) < 10){
      newDay  = newDay + '-' + '0' + (getDate.getMonth()+1);
    } else newDay  = newDay + '-' + (getDate.getMonth()+1);
    if (getDate.getDate() < 10) {
        newDay  = newDay + '-' + '0' + (getDate.getDate());
    } else newDay  = newDay + '-' + (getDate.getDate());


    return {
      ascending: true,
      currentPage: 1,
      elementsPerPage: 3,
      sortColumn: 'idTodo',
      editing : false,
      edit : 'Edit',
      todos: defaultData,
      newTodoTitel : '',
      newToDoEntry: {
        titel : '',
        description : '',
        dueDate : '',
        isDone: ''
      },
      newTodoDescription : '',
      newTodoDueDate : '',
      today : newDay
    }
  },
  computed: {
    "columns": function columns() {
      if (this.todos.length == 0) {
        return [];
      }
      return Object.keys(this.todos[0])
    }
  },
  mounted(){
    this.getEntries()
  },
  methods :{
          "sortTable": function sortTable(col) {
                          if (this.sortColumn === col) {
                            this.ascending = !this.ascending;
                          } else {
                            this.ascending = true;
                            this.sortColumn = col;
                          }

                          var ascending = this.ascending;

                          this.todos.sort(function(a, b) {
                            if (a[col] > b[col]) {
                              return ascending ? 1 : -1
                            } else if (a[col] < b[col]) {
                              return ascending ? -1 : 1
                            }
                            return 0;
                          })
            },
            "num_pages": function num_pages() {
                          return Math.ceil(this.todos.length / this.elementsPerPage);
            },
            "get_rows": function get_rows() {
                          var start = (this.currentPage-1) * this.elementsPerPage;
                          var end = start + this.elementsPerPage;
                          return this.todos.slice(start, end);
            },
            "change_page": function change_page(page) {
                          this.currentPage = page;
            },
    getEntries(){
      axios
      .get("api/todos/all")
      .then(response =>{
        var newarray = [];
        response.data.forEach(element =>{
          var newentry = {idTodo: element.idTodo, titel: element.titel, description: element.description, 
                          dueDate: element.dueDate ,isDone : element.isDone, actions : '', edit : 'Edit'};
          newarray.push(newentry);
        })
        this.todos = newarray;
        })
       .catch((error)=> window.alert(error)) 
    },
    editTodo(index) {

      var findId = this.todos.findIndex(e =>e.idTodo===index);

      if(this.todos[findId].edit === "Edit"){
        this.todos[findId].edit = "Save";
        this.newTodoTitel = this.todos[findId].titel;
        this.newTodoDescription = this.todos[findId].description;
        this.newTodoDueDate = this.todos[findId].dueDate;
      } else {
        this.todos.forEach(e => e.edit = "Edit");
        this.saveChanges(findId);
      }
      
    },
    saveChanges(index){
      if (this.newTodoDueDate > this.today){
       
        this.newToDoEntry.titel = this.newTodoTitel;
        this.newToDoEntry.description = this.newTodoDescription;
        this.newToDoEntry.dueDate = this.newTodoDueDate;
        this.newToDoEntry.isDone = this.todos[index].isDone;

        axios
        .put("api/todos/" + this.todos[index].idTodo , this.newToDoEntry)
        .catch((error)=>window.alert(error));

        //this.getEntries();


      this.todos[index].titel=this.newToDoEntry.titel;
      this.todos[index].description=this.newToDoEntry.description;
      this.todos[index].dueDate=this.newToDoEntry.dueDate;
      this.todos[index].isDone=this.newToDoEntry.isDone;
      } else window.alert(" No entries for past time allowed ")
      
      this.editing = !this.editing;

      this.newTodoTitel =  '',
      this.newTodoDescription = '',
      this.newTodoDueDate = ''
    },
    addTodo () {
      if (this.newTodoTitel&&this.newTodoDescription&&this.newTodoDueDate){
        if (this.newTodoDueDate > this.today){

          this.newToDoEntry.titel = this.newTodoTitel;
          this.newToDoEntry.description = this.newTodoDescription;
          this.newToDoEntry.dueDate = this.newTodoDueDate;
          this.newToDoEntry.isDone = false;

          axios
            .post("/api/todos",this.newToDoEntry)
            .catch((error)=>window.alert(error));

          //this.getEntries();

          this.todos.push({
          idTodo: this.todos[this.todos.length-1].idTodo + 1,
          titel: this.newToDoEntry.titel,
          description: this.newToDoEntry.description,
          dueDate: this.newToDoEntry.dueDate,
          isDone: this.newToDoEntry.isDone,
          actions : '', 
          edit : 'Edit'
          });
          
          this.newTodoTitel =  '';
          this.newTodoDescription = '';
          this.newTodoDueDate = '';
         
        } else window.alert(" No entries for past time allowed ")
      }
      
    },
    removeTodo (index) {

      axios
      .delete("api/todos/"+index)
      .catch((error)=>window.alert(error));
      
      //this.getEntries();
      this.todos.splice(this.todos.findIndex(e =>e.idTodo===index), 1);
      // index enthält die idTodo, an der der Eintrag in der DB gelöscht werden soll
    }, 
    isDone(index){
      // finde die richtige idTodo zum index und verändere dort den Wert
      var findId = this.todos.findIndex(e =>e.idTodo===index);
      this.todos[findId].isDone = !this.todos[findId].isDone;

      this.newToDoEntry.titel = this.todos[findId].titel;
      this.newToDoEntry.description = this.todos[findId].description;
      this.newToDoEntry.dueDate = this.todos[findId].dueDate;
      this.newToDoEntry.isDone = this.todos[findId].isDone;

      axios
        .put("api/todos/" + index , this.newToDoEntry)
        .catch((error)=>window.alert(error));

      //this.getEntries()    
    }
  }
}

