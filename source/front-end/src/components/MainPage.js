import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import './MainPage.css';

import Article from './Article/Article.js';
import ArticlesBody from './ArticlesBody/ArticlesBody';

export default class MainPage extends Component {

    constructor(props){
        super(props);
        
        this.state = this.props.stateProp;
    }

    render()
    {
     return(
        <div className="MainPage">
            <Switch>
                <Route exact  path='/' render={ () => <ArticlesBody stateProp={this.state} />} />
                <Route path='/Article' render={ () => <Article stateProp={this.state} />} />
            </Switch>
        </div>
        );
    }
}