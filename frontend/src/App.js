import './App.css';
import React, { Component } from "react";
import {Table, Container, Input, Button} from 'reactstrap';

class App extends Component {
  constructor(props) {
    super(props);
    // our states
    this.state = {
      users: [],
      filteredUsers: [],
      countries: [],
      selected: ""
    }

    this.handleChange = this.handleChange.bind(this);
    this.handleButtonClick = this.handleButtonClick.bind(this);
  }


  async componentDidMount() {
    const response = await fetch('api/users/get');
    const body = await response.json();
    const countries = ['all'];
    const users = [];
    body['countries'].forEach(country => {
      countries.push(country['name']);
      country.users.forEach(user => {
        let user_complete = {
          'country': country['name'],
          'name': user['name'],
          'gender': user['gender'],
          'email': user['email']
        }
        users.push(user_complete);
      })
    });

    this.setState({
      users: users,
      filteredUsers: users,
      countries: countries,
      selected: ""
    });
  }

  handleChange(e) {
    this.filter(e.target.value);
  };

  async handleButtonClick(e) {
    const actualSelect = this.state.selected;
    const response = await fetch('api/users/get');
    const body = await response.json();
    const countries = ['all'];
    const users = [];
    body['countries'].forEach(country => {
      countries.push(country['name']);
      country.users.forEach(user => {
        let user_complete = {
          'country': country['name'],
          'name': user['name'],
          'gender': user['gender'],
          'email': user['email']
        }
        users.push(user_complete);
      })
    });
    this.setState({
      users: users,
      filteredUsers: users,
      countries: countries,
      selected: actualSelect
    });
    // call filter again to keep filtered view
    this.filter(actualSelect);
  }

  filter(selectString) {
    // filters the users by country
    let allUsers = this.state.users;
    // if all is selected, it shows all users
    if (selectString === "all") {
      this.setState({filteredUsers: allUsers})
    } else {
      let filteredList = allUsers.filter(function(el) {
        return el.country === selectString;
      })
      this.setState({filteredUsers: filteredList})
    }
    this.setState({ selected: selectString });
  }

  render() {
    const {filteredUsers, countries} = this.state;

    const userList = filteredUsers.map(user => {
      return <tr>
        <td>{user.name}</td>
        <td>{user.gender}</td>
        <td>{user.email}</td>
        </tr>
    })

    const selectOption = countries.map(country => {
      return <option value={country}>{country}</option>
    })

    return (
        <div className="App">
          <Container fluid>
              <div className="card m-4">
                <div className="card-header">
                  <h2>Users with Countries</h2>
                </div>
                <div className="card-body">
                  <Container className="bg-light border p-4 mb-4">
                    <div className='row'>
                      <div className='col-6'>
                        <Input type={"select"} size='1' value={this.state.selected} onChange={this.handleChange}>
                          {selectOption}
                        </Input>
                      </div>
                      <div className='col-6'>
                        <Button color="primary" onClick={this.handleButtonClick}>Refresh</Button>
                      </div>
                    </div>
                    </Container>
                  
                  <div>
                    <h3>Users</h3>
                    <Table className="table-striped table-bordered mt-4">
                      <thead>
                        <tr>
                          <th>Name</th>
                          <th>Gender</th>
                          <th>Email</th>
                        </tr>
                      </thead>
                      <tbody>
                        {userList}
                      </tbody>           
                    </Table>
                  </div>
                </div>
              </div>

          </Container>
        </div>
    );
  }
}
export default App;
