import React from 'react';

const greenStyle = {
    color: 'green',
};

const redStyle = {
    color: 'red',
};


class Todo extends React.Component {
    constructor(props) {
        super(props);
        this.setDone = this.setDone.bind(this);
        this.state = {done: null};
    }

    static getDerivedStateFromProps(props, state) {
        if (state.done === null)
            return {done: props.done}; else return {};
    }

    setDone = () => {
        if (this.state.done) {
            this.setState({done: false}, this.props.update(this.props.id, this.state.done));
        } else {
            this.setState({done: true}, this.props.update(this.props.id, this.state.done));
        }

    }

    render() {

        const {value} = this.props;
        const {done} = this.state;

        return (
            <div>
                {done === true ?
                    <li style={greenStyle}>
                        {value}
                    </li> :
                    <li style={redStyle}>
                        {value}
                    </li>}
                <button onClick={this.setDone}>
                    Change status
                </button>
            </div>
        )

    }
    ;
}

export default Todo;