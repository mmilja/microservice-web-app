import React, { Component } from 'react';
import './ArticlesBody.css';

import ArticleHighlight from '../ArticleHighlight/ArticleHighlight';

export default class ArticlesBody extends Component {

    constructor(props){
        super(props);
        
        this.state = this.props.stateProp;
    }

    handleTitleChange = (title) => {
        this.props.onArticleClick(title);
    }

    render()
    {
     return(
        <div className="ArticlesBody">   
            {this.props.stateProp.articleHighlight.map(artItem => <ArticleHighlight articleProp={artItem} onArticleClick={this.props.onArticleClick}/>)}
        </div>
        );
    }
}