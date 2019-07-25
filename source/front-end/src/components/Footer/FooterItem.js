import React, { Component } from 'react';
import './FooterItem.css';

import { Link } from 'react-router-dom'

export default class FooterItem extends Component {

    constructor(props) {
        super(props);

        this._handleClick = this._handleClick.bind(this);
    }

        render() {
            return(
                <p className="FooterItem"><Link to={"/" + this.props.description} onClick={this._handleClick} >{this.props.description}</Link></p>
            );
        }

        _handleClick(event){
            this.props.getState(this.props.description);
        }

}