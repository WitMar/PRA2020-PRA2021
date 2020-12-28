import React from 'react';

const greenStyle = {
    backgroundColor: 'green',
};

const redStyle = {
    backgroundColor: 'red',
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
            <React.Fragment>
                {done === true ?
                    <th style={greenStyle}>
                       <textarea rows="4" cols="100"
                                 value={value}
                       />
                    </th> :
                    <th style={redStyle}>
                        <textarea rows="4" cols="100"
                                  value={value}
                        />
                    </th>}
                <th>
                    <button onClick={this.setDone}>
                        Change status
                    </button>
                </th>
            </React.Fragment>
        )

    }
}

export default Todo;