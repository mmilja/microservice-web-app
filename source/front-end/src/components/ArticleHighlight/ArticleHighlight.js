import React, { Component } from 'react';
import { Route } from 'react-router-dom';
import './ArticleHighlight.css';


export default class ArticleHighlight extends Component {

    constructor(props){
        super(props);
        
        this.state = this.props.stateProp;
    }

    render() {
    return (
        <div className="ArticleHighlight">
            <div className="article-image">
                <Route path="Article"><img src={`data:image/jpeg;base64,${this.state.articleHighlight.image}`} alt="Article" /></Route>
            </div>
            <div className="article-title">
                <Route path="Article"><h1>{this.state.articleHighlight.title}</h1></Route>
            </div>
        </div>
    )
    }
}