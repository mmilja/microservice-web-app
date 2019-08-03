import React, { Component } from 'react';
import './ArticleHighlight.css';
import { Link } from 'react-router-dom'


export default class ArticleHighlight extends Component {

    constructor(props){
        super(props);
        
        this.state = {
            article : this.props.articleProp
        } 
    }

    handleTitleChange = () => {
        var title = this.state.article.title
        this.props.onArticleClick(title)
    }

    render() {
    return (
        <div className="ArticleHighlight">
            <div className="article-image">
                <Link to={{ pathname: "/Article", search: `?title=${this.state.article.title}`, state: this.state.article.title }}  onClick={this.handleTitleChange} ><img src={`data:image/jpeg;base64,${this.state.article.image}`} alt="Article" /></Link>
            </div>
            <div className="article-title">
                <Link to={{ pathname: "/Article", search: `?title=${this.state.article.title}`, state: this.state.article.title }} onClick={this.handleTitleChange} ><h1>{this.state.article.title}</h1></Link>
            </div>
        </div>
    )
    }
}