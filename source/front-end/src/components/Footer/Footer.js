import React, { Component } from 'react';
import './Footer.css';

import FooterItem from './FooterItem';

export default class Footer extends Component {

    constructor(props){
        super(props);

        this.state = this.props.stateProp;

        this._getState = this._getState.bind(this);
    }

    render()
    {
        return(
        <footer id="myFooter">    
            <span className="footer-text">MicroPapers @2019.</span>
            {this.state.infoList.map(navItem => <FooterItem getState={this._getState} description={navItem} key={navItem} />)}   
        </footer>
     );
    }

    _getState(description){
        this.setState({currentState: description});
        this.props.appGetState(description);
    }
}