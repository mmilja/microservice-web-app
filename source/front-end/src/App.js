import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import './App.css';

import MainPage from './components/MainPage.js';
import Navbar from './components/Navbar/Navbar.js';
import Help from './components/InfoComponents/Help.js';
import About from './components/InfoComponents/About.js';
import Footer from './components/Footer/Footer.js';

export default class App extends Component {

    constructor(props){
        super(props);

        this.state = {
            infoList: ['Home','About','Help'],
            categoryList: ['Home','News','Show','Sport','Lifestyle'],
            currentState: 'Home',
            type: 'normal',
            articleHighlight: [],
            article: []
        };

        this._appGetState = this._appGetState.bind(this);
    }

    _appGetState(description){
        this.setState({currentState: description});
    }


    render() {
        return(
            <div id="App" className={this.state.type}>
                <Navbar appGetState={this._appGetState} stateProp={this.state} />
                <Switch>
                    <Route exact  path='/' render={ () => <MainPage appGetState={this._appGetState} stateProp={this.state} />} />
                    <Route path='/Home' render={ () => <MainPage appGetState={this._appGetState} stateProp={this.state} />} />
                    <Route path='/Help'  render={ () => <Help appGetState={this._appGetState} stateProp={this.state} /> } />
                    <Route path='/About'  render={ () => <About appGetState={this._appGetState} stateProp={this.state} /> } />
                </Switch>
                <Footer appGetState={this._appGetState} stateProp={this.state} />
            </div> 
        );
    }
}