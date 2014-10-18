require 'sinatra'
require 'json'

set :username,'dan'
set :password,'test'
set :token,'abc123'

before do
  content_type :json
  
  headers 'Access-Control-Allow-Origin' => '*', 
    'Access-Control-Allow-Methods' => ['OPTIONS', 'GET', 'POST', 'PUT', 'DELETE'],
    'Access-Control-Allow-Headers' => 'Content-Type, X-Auth-Token'
end

def protected!
  return unless request.env['HTTP_X_AUTH_TOKEN'] != settings.token
  halt 401, 'Not Authorized'
end

options '*' do
  200
end

post '/login' do
  # for angular since it sends request as JSON instead of x-www-form-urlencoded
  params = JSON.parse(request.env['rack.input'].read)
  
  if params['username'] == settings.username && params['password'] == settings.password
    { token: settings.token }.to_json
  else
    halt 401, 'Not Authorized'
  end
end

get '/books' do
  protected!
  { message: "Some books that needed a token" }.to_json
end