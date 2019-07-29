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
        var uri = '/api/article/recent';

        console.log("Executing the rest query at: " + uri)

        fetch(uri)
        .then(res => {
            console.log(res);
            res.json();
        })
        .then((data) => {
            console.log(data);
            this.setState({articleHighlight: data});
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