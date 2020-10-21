import React, { Component } from 'react'

export default class UpdateProject extends Component {
    render() {
        return (
        <div>
            <div className="project">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Create Edit Project form</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}> 
                                <div className="form-group">
                                    <input 
                                    type="text" 
                                    className="form-control form-control-lg" 
                                    placeholder="Project Name" 
                                    name="projectName" />
                                </div>
                               
                                
                                <div className="form-group">
                                    <input 
                                    type="text" 
                                    className="form-control form-control-lg" 
                                    placeholder="Unique Project ID" disabled
                                    name="projectIdentifier" />
                                </div>
                                
                                <div className="form-group">
                                    <textarea 
                                    className="form-control form-control-lg"
                                    placeholder="Project Description"
                                    name="description" >
                                    </textarea>
                                </div>
                               
                                <h6>Start Date</h6>
                                <div className="form-group">
                                    <input 
                                    type="date" 
                                    className="form-control form-control-lg" 
                                    name="start_date"/>
                                </div>
                                <h6>Estimated End Date</h6>
                                <div className="form-group">
                                    <input 
                                    type="date" 
                                    className="form-control form-control-lg" 
                                    name="end_date" />
                                </div>

                                <input 
                                type="submit" 
                                className="btn btn-primary btn-block mt-4" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
    }
}
