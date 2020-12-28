import React from 'react';
import Todo from "../Todo/Todo";

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

    updateChild = (id, done) => {
        let arrAfterUpdate = this.state.todosList.map(function (item) {
            if(item.id === id) item.done = done;
            return item;
        });
        this.setState({todosList: arrAfterUpdate});
        console.log(arrAfterUpdate);
    };


    addTodo = () => {
        this.counter++;
        let newelement = {id: this.counter, value: this.state.newTodo, done: false};
        this.setState(prevState => ({
            todosList: [...prevState.todosList, newelement]
        }));
    };

    handleRemove = id => {
        let arrAfterDel = this.state.todosList.filter(function (item) {
            return item.id !== id
        });
        this.setState({todosList: arrAfterDel});
    }

    myChangeHandler = (event) => {
        this.setState({newTodo: event.target.value});
    }

    render() {

        const {todosList} = this.state;

        let todos = todosList.map(todo => {
            return (<ul key={todo.id}>
                <Todo value={todo.value}
                      done={todo.done}
                      id = {todo.id}
                      update={() => this.updateChild}  />
                <button type="button" onClick={() => this.handleRemove(todo.id)}>
                    Remove
                </button>
            </ul>);
        })

        return (
            <div className="TodoList">
                <h1>Todo List {this.props.name}</h1>
                {todos}
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


