import React from 'react';
import Todo from "../Todo/Todo";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';


class TodoList extends React.Component {
    constructor(props) {
        super(props);
        let t1 = {id: 0, value: "Learn React", done: true};
        let t2 = {id: 1, value: "Pass Programming Laboratory", done: false};
        let t3 = {id: 2, value: "Have a nice holiday", done: false};
        this.state = {todosList: [t1, t2, t3]};
        this.addTodo = this.addTodo.bind(this);
        this.counter = 2;
    };

    componentDidMount() {
        axios.get(`http://localhost:8080/todos`)
            .then(res => {
                const todosList = res.data;
                this.setState({todosList});
            })
    }

    updateChild = (id, done) => {
        let arrAfterUpdate = this.state.todosList.map(function (item) {
            if (item.id === id) item.done = done;
            return item;
        });

        let object = arrAfterUpdate.find(function (item) {
            if (item.id === id) return item;
        });

        this.setState({todosList: arrAfterUpdate});

        axios.put(`http://localhost:8080/todos/${object.id}`, object);

        console.log(arrAfterUpdate);
    };


    addTodo = () => {
        this.counter++;
        let newelement = {id: this.counter, value: this.state.newTodo, done: false};
        this.setState(prevState => ({
            todosList: [...prevState.todosList, newelement]
        }));
        axios.post(`http://localhost:8080/todos/`, newelement);
    };

    handleRemove = id => {
        let object = this.state.todosList.find(function (item) {
            if (item.id === id) return item;
        });
        let arrAfterDel = this.state.todosList.filter(function (item) {
            return item.id !== id
        });
        this.setState({todosList: arrAfterDel});

        axios.delete(`http://localhost:8080/todos/${object.id}`, object);
    }

    myChangeHandler = (event) => {
        this.setState({newTodo: event.target.value});
    }

    render() {

        const {todosList} = this.state;

        let todos = todosList.map(todo => {
            return (<tr key={todo.id}>
                <Todo value={todo.value}
                      done={todo.done}
                      id={todo.id}
                      update={(id, done) => this.updateChild(id, done)}/>
                <th>
                    <button type="button" onClick={() => this.handleRemove(todo.id)}>
                        Remove
                    </button>
                </th>
            </tr>);
        })

        return (
            <div className="TodoList">
                <h1>Todo List {this.props.name}</h1>
                <table className="table table-striped">
                    <thead className="thead-dark">
                    <th scope="col">Todo</th>
                    <th scope="col">Status</th>
                    <th scope="col"></th>
                    </thead>
                    <tbody>
                    {todos}
                    </tbody>
                </table>
                <p> My new todo </p>
                <input
                    type='text'
                    onChange={this.myChangeHandler}
                />
                <button onClick={this.addTodo}>
                    AddTodo
                </button>
            </div>
        );
    }
}

export default TodoList;


