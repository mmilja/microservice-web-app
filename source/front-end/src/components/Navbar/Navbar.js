import React, { Component } from 'react';
import './Navbar.css';

import NavbarItem from './NavbarItem.js';
import NavbarPicture from './NavbarPicture.js'

export default class Navbar extends Component {

    constructor(props){
        super(props);

        this.state = {
            navigationList: this.props.stateProp.categoryList,
            currentState: this.props.stateProp.currentState 
        };

        this._getState = this._getState.bind(this);
    }

    render() {
        return(
        <ul className="Navigation">
            <NavbarPicture getState={this._getState}/>
            {this.state.navigationList.map(navItem => <NavbarItem getState={this._getState} description={navItem} key={navItem} />)}
        </ul>     
        );
    }

    _getState(description){
        this.setState({currentState: description});
        this.props.appGetState(description);
    }

}