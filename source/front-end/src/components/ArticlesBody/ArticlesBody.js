import React, { Component } from 'react';
import Base64 from 'base-64';
import './ArticlesBody.css';

import ArticleHighlight from '../ArticleHighlight/ArticleHighlight';

export default class ArticlesBody extends Component {

    constructor(props){
        super(props);
        
        this.state = this.props.stateProp;
    }
    
    componentDidMount() {
        let restHeaders = new Headers();
        var uri = 'http://backend/article/recent';
        restHeaders.set('Authorization', 'Basic ' + Base64.encode("Database:Access"));


        fetch(uri, {
            method: 'GET',
            headers: restHeaders})
        .then(res => res.json())
        .then((data) => {
            this.setState({articleHighlight: data});
            console.log(data);
        })
        .catch(console.log)
    }

    render()
    {
     return(
        <div className="ArticlesBody">   
            <ArticleHighlight stateProp={this.state} />
        </div>
        );
    }
}