import React, { Component } from 'react';
import './NavbarItem.css';

import { Link } from 'react-router-dom'

export default class NavbarItem extends Component {

    constructor(props) {
        super(props);

        this._handleClick = this._handleClick.bind(this);
    }

        render() {
            return(
                <li className="NavItem"><Link to={"/" + this.props.description} onClick={this._handleClick} >{this.props.description}</Link></li>
            );
        }

        _handleClick(event){
            this.props.getState(this.props.description);
        }

}